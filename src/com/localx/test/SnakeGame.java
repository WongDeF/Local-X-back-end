package com.localx.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 贪吃蛇游戏
 */
public class SnakeGame extends JFrame {
    
    private GamePanel gamePanel;
    
    public SnakeGame() {
        setTitle("贪吃蛇游戏");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        gamePanel = new GamePanel();
        add(gamePanel);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SnakeGame());
    }
}

/**
 * 游戏面板
 */
class GamePanel extends JPanel implements ActionListener {
    
    private final int BOARD_WIDTH = 600;
    private final int BOARD_HEIGHT = 400;
    private final int UNIT_SIZE = 25;
    private final int GAME_UNITS = (BOARD_WIDTH * BOARD_HEIGHT) / UNIT_SIZE;
    private final int DELAY = 100;
    
    // 蛇的身体
    private final List<Point> snake;
    
    // 食物位置
    private Point food;
    
    // 蛇的移动方向
    private char direction;
    
    // 游戏状态
    private boolean running;
    private int score;
    private Timer timer;
    private Random random;
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        
        random = new Random();
        snake = new ArrayList<>();
        
        startGame();
    }
    
    public void startGame() {
        // 初始化蛇
        snake.clear();
        snake.add(new Point(0, 0));
        snake.add(new Point(UNIT_SIZE, 0));
        snake.add(new Point(UNIT_SIZE * 2, 0));
        
        direction = 'R'; // 初始向右
        running = true;
        score = 0;
        
        // 生成第一个食物
        newFood();
        
        // 启动游戏循环
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        draw(g);
    }
    
    public void draw(Graphics g) {
        if (running) {
            // 绘制网格线（可选）
            /*
            for (int i = 0; i < BOARD_HEIGHT / UNIT_SIZE; i++) {
                g.setColor(Color.DARK_GRAY);
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, BOARD_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, BOARD_WIDTH, i * UNIT_SIZE);
            }
            */
            
            // 绘制食物
            g.setColor(Color.RED);
            g.fillOval(food.x, food.y, UNIT_SIZE, UNIT_SIZE);
            
            // 绘制蛇
            for (Point point : snake) {
                // 蛇头用不同颜色
                if (point.equals(snake.get(snake.size() - 1))) {
                    g.setColor(new Color(45, 180, 0));
                } else {
                    g.setColor(new Color(90, 255, 0));
                }
                g.fillRect(point.x, point.y, UNIT_SIZE, UNIT_SIZE);
                
                // 绘制边框
                g.setColor(Color.BLACK);
                g.drawRect(point.x, point.y, UNIT_SIZE, UNIT_SIZE);
            }
            
            // 绘制分数
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("分数: " + score, 
                    (BOARD_WIDTH - metrics.stringWidth("分数: " + score)) / 2, 
                    g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }
    
    public void move() {
        // 获取蛇头当前位置
        Point head = snake.get(snake.size() - 1);
        Point newHead = new Point(head.x, head.y);
        
        // 根据方向移动
        switch (direction) {
            case 'U': // 上
                newHead.y -= UNIT_SIZE;
                break;
            case 'D': // 下
                newHead.y += UNIT_SIZE;
                break;
            case 'L': // 左
                newHead.x -= UNIT_SIZE;
                break;
            case 'R': // 右
                newHead.x += UNIT_SIZE;
                break;
        }
        
        // 添加新头部
        snake.add(newHead);
        
        // 检查是否吃到食物
        if (newHead.equals(food)) {
            score++;
            newFood();
        } else {
            // 移除尾部
            snake.remove(0);
        }
    }
    
    public void newFood() {
        int x, y;
        boolean validPosition;
        
        do {
            validPosition = true;
            x = random.nextInt((int)(BOARD_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
            y = random.nextInt((int)(BOARD_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
            
            // 确保食物不在蛇身上
            for (Point point : snake) {
                if (point.x == x && point.y == y) {
                    validPosition = false;
                    break;
                }
            }
        } while (!validPosition);
        
        food = new Point(x, y);
    }
    
    public void checkCollisions() {
        Point head = snake.get(snake.size() - 1);
        
        // 检查是否撞墙
        if (head.x < 0 || head.x >= BOARD_WIDTH || head.y < 0 || head.y >= BOARD_HEIGHT) {
            running = false;
        }
        
        // 检查是否撞到自己
        for (int i = 0; i < snake.size() - 1; i++) {
            if (head.equals(snake.get(i))) {
                running = false;
                break;
            }
        }
        
        // 如果游戏结束，停止计时器
        if (!running) {
            timer.stop();
        }
    }
    
    public void gameOver(Graphics g) {
        // 游戏结束文字
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("游戏结束", 
                (BOARD_WIDTH - metrics1.stringWidth("游戏结束")) / 2, 
                g.getFont().getSize());
        
        // 显示分数
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("最终分数: " + score, 
                (BOARD_WIDTH - metrics2.stringWidth("最终分数: " + score)) / 2, 
                g.getFont().getSize() * 3);
        
        // 重新开始提示
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 15));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("按空格键重新开始", 
                (BOARD_WIDTH - metrics3.stringWidth("按空格键重新开始")) / 2, 
                g.getFont().getSize() * 5);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkCollisions();
        }
        repaint();
    }
    
    /**
     * 键盘监听器
     */
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    if (!running) {
                        startGame();
                    }
                    break;
            }
        }
    }
}
