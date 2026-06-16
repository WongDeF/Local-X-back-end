package com.localx;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;

/**
 * Binance Spot API - 市场数据端点实现
 * 文档: https://developers.binance.com/docs/zh-CN/binance-spot-api-docs/rest-api/market-data-endpoints
 */
public class binance {
    
    private static final String BASE_URL = "https://api.binance.com";
    
    /**
     * 发送GET请求
     * @param endpoint API端点
     * @param params 查询参数
     * @return 响应字符串
     */
    public static String sendGetRequest(String endpoint, Map<String, String> params) throws Exception {
        StringBuilder urlBuilder = new StringBuilder(BASE_URL + endpoint);
        if (params != null && !params.isEmpty()) {
            urlBuilder.append("?");
            boolean first = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (!first) {
                    urlBuilder.append("&");
                }
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue());
                first = false;
            }
        }
        
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();
        
        return response.toString();
    }
    
    /**
     * 1. 测试服务器连通性
     * GET /api/v3/ping
     * @return 空对象 {}
     */
    public static String testConnectivity() throws Exception {
        return sendGetRequest("/api/v3/ping", null);
    }
    
    /**
     * 2. 获取服务器时间
     * GET /api/v3/time
     * @return 服务器时间
     */
    public static String getServerTime() throws Exception {
        return sendGetRequest("/api/v3/time", null);
    }
    
    /**
     * 3. 获取交易对信息
     * GET /api/v3/exchangeInfo
     * @param symbol 交易对符号(可选)
     * @param symbols 交易对符号列表(可选)
     * @return 交易对信息
     */
    public static String getExchangeInfo(String symbol, String symbols) throws Exception {
        Map<String, String> params = new HashMap<>();
        if (symbol != null) {
            params.put("symbol", symbol);
        }
        if (symbols != null) {
            params.put("symbols", symbols);
        }
        return sendGetRequest("/api/v3/exchangeInfo", params.isEmpty() ? null : params);
    }
    
    /**
     * 4. 获取深度信息
     * GET /api/v3/depth
     * @param symbol 交易对符号
     * @param limit 限制数量，默认100，最大5000
     * @return 深度信息
     */
    public static String getOrderBook(String symbol, Integer limit) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("symbol", symbol);
        if (limit != null) {
            params.put("limit", String.valueOf(limit));
        }
        return sendGetRequest("/api/v3/depth", params);
    }
    
    /**
     * 5. 获取近期成交列表
     * GET /api/v3/trades
     * @param symbol 交易对符号
     * @param limit 限制数量，默认500，最大1000
     * @return 成交列表
     */
    public static String getRecentTrades(String symbol, Integer limit) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("symbol", symbol);
        if (limit != null) {
            params.put("limit", String.valueOf(limit));
        }
        return sendGetRequest("/api/v3/trades", params);
    }
    
    /**
     * 6. 获取历史成交列表
     * GET /api/v3/historicalTrades
     * @param symbol 交易对符号
     * @param limit 限制数量，默认500，最大1000
     * @param fromId 从指定的tradeId开始返回
     * @return 历史成交列表
     */
    public static String getHistoricalTrades(String symbol, Integer limit, Long fromId) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("symbol", symbol);
        if (limit != null) {
            params.put("limit", String.valueOf(limit));
        }
        if (fromId != null) {
            params.put("fromId", String.valueOf(fromId));
        }
        return sendGetRequest("/api/v3/historicalTrades", params);
    }
    
    /**
     * 7. 获取聚合交易列表
     * GET /api/v3/aggTrades
     * @param symbol 交易对符号
     * @param fromId 从指定的aggTradeId开始返回
     * @param startTime 起始时间
     * @param endTime 结束时间
     * @param limit 限制数量，默认500，最大1000
     * @return 聚合交易列表
     */
    public static String getAggTrades(String symbol, Long fromId, Long startTime, 
                                      Long endTime, Integer limit) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("symbol", symbol);
        if (fromId != null) {
            params.put("fromId", String.valueOf(fromId));
        }
        if (startTime != null) {
            params.put("startTime", String.valueOf(startTime));
        }
        if (endTime != null) {
            params.put("endTime", String.valueOf(endTime));
        }
        if (limit != null) {
            params.put("limit", String.valueOf(limit));
        }
        return sendGetRequest("/api/v3/aggTrades", params);
    }
    
    /**
     * 8. 获取K线数据
     * GET /api/v3/klines
     * @param symbol 交易对符号
     * @param interval K线间隔
     * @param startTime 起始时间
     * @param endTime 结束时间
     * @param limit 限制数量，默认500，最大1000
     * @return K线数据
     */
    public static String getKlines(String symbol, String interval, Long startTime, 
                                   Long endTime, Integer limit) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("interval", interval);
        if (startTime != null) {
            params.put("startTime", String.valueOf(startTime));
        }
        if (endTime != null) {
            params.put("endTime", String.valueOf(endTime));
        }
        if (limit != null) {
            params.put("limit", String.valueOf(limit));
        }
        return sendGetRequest("/api/v3/klines", params);
    }
    
    /**
     * 9. 获取当前平均价格
     * GET /api/v3/avgPrice
     * @param symbol 交易对符号
     * @return 平均价格
     */
    public static String getCurrentAvgPrice(String symbol) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("symbol", symbol);
        return sendGetRequest("/api/v3/avgPrice", params);
    }
    
    /**
     * 10. 获取24小时价格变动统计
     * GET /api/v3/ticker/24hr
     * @param symbol 交易对符号(可选)
     * @param symbols 交易对符号列表(可选)
     * @return 24小时价格变动统计
     */
    public static String getTicker24hr(String symbol, String symbols) throws Exception {
        Map<String, String> params = new HashMap<>();
        if (symbol != null) {
            params.put("symbol", symbol);
        }
        if (symbols != null) {
            params.put("symbols", symbols);
        }
        return sendGetRequest("/api/v3/ticker/24hr", params.isEmpty() ? null : params);
    }
    
    /**
     * 11. 获取最新价格
     * GET /api/v3/ticker/price
     * @param symbol 交易对符号(可选)
     * @param symbols 交易对符号列表(可选)
     * @return 最新价格
     */
    public static String getTickerPrice(String symbol, String symbols) throws Exception {
        Map<String, String> params = new HashMap<>();
        if (symbol != null) {
            params.put("symbol", symbol);
        }
        if (symbols != null) {
            params.put("symbols", symbols);
        }
        return sendGetRequest("/api/v3/ticker/price", params.isEmpty() ? null : params);
    }
    
    /**
     * 12. 获取最优挂单
     * GET /api/v3/ticker/bookTicker
     * @param symbol 交易对符号(可选)
     * @param symbols 交易对符号列表(可选)
     * @return 最优挂单
     */
    public static String getBookTicker(String symbol, String symbols) throws Exception {
        Map<String, String> params = new HashMap<>();
        if (symbol != null) {
            params.put("symbol", symbol);
        }
        if (symbols != null) {
            params.put("symbols", symbols);
        }
        return sendGetRequest("/api/v3/ticker/bookTicker", params.isEmpty() ? null : params);
    }
    
    /**
     * 13. 获取组合行情ticker
     * GET /api/v3/ticker/tradingDay
     * @param symbol 交易对符号
     * @param timeZone 时区(可选)
     * @param type ticker类型(可选)
     * @return 组合行情ticker
     */
    public static String getTradingDayTicker(String symbol, String timeZone, String type) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("symbol", symbol);
        if (timeZone != null) {
            params.put("timeZone", timeZone);
        }
        if (type != null) {
            params.put("type", type);
        }
        return sendGetRequest("/api/v3/ticker/tradingDay", params);
    }
    
    /**
     * 14. 获取滚动窗口价格变动统计
     * GET /api/v3/ticker
     * @param symbol 交易对符号(可选)
     * @param symbols 交易对符号列表(可选)
     * @param windowSize 窗口大小
     * @param type ticker类型(可选)
     * @return 滚动窗口价格变动统计
     */
    public static String getRollingWindowTicker(String symbol, String symbols, 
                                                String windowSize, String type) throws Exception {
        Map<String, String> params = new HashMap<>();
        if (symbol != null) {
            params.put("symbol", symbol);
        }
        if (symbols != null) {
            params.put("symbols", symbols);
        }
        if (windowSize != null) {
            params.put("windowSize", windowSize);
        }
        if (type != null) {
            params.put("type", type);
        }
        return sendGetRequest("/api/v3/ticker", params.isEmpty() ? null : params);
    }
    
    // ==================== 测试示例 ====================
    
    public static void main(String[] args) {
        try {
            // 测试服务器连通性
            System.out.println("1. 测试连通性:");
            System.out.println(testConnectivity());
            System.out.println();
            
            // 获取服务器时间
            System.out.println("2. 服务器时间:");
            System.out.println(getServerTime());
            System.out.println();
            
            // 获取BTCUSDT的最新价格
            System.out.println("3. BTCUSDT最新价格:");
            System.out.println(getTickerPrice("BTCUSDT", null));
            System.out.println();
            
            // 获取BTCUSDT的24小时统计
            System.out.println("4. BTCUSDT 24小时统计:");
            System.out.println(getTicker24hr("BTCUSDT", null));
            System.out.println();
            
            // 获取BTCUSDT的深度信息
            System.out.println("5. BTCUSDT深度信息:");
            System.out.println(getOrderBook("BTCUSDT", 10));
            System.out.println();
            
            // 获取BTCUSDT的K线数据
            System.out.println("6. BTCUSDT K线数据(1小时):");
            System.out.println(getKlines("BTCUSDT", "1h", null, null, 5));
            System.out.println();
            
            // 获取BTCUSDT的近期成交
            System.out.println("7. BTCUSDT近期成交:");
            System.out.println(getRecentTrades("BTCUSDT", 5));
            System.out.println();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
