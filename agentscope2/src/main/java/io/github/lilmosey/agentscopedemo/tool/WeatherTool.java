package io.github.lilmosey.agentscopedemo.tool;

import io.agentscope.core.tool.Tool;
import io.agentscope.core.tool.ToolParam;

public class WeatherTool {
    public static String DEFAULT_MSG = "今天的天气是";

    @Tool(name = "getWeather",description = "获取天气信息")
    public String getWeather(@ToolParam(name = "city",description = "城市") String city){
        System.out.println("-----"+ city);
        if("北京".equals(city)){
            return city + DEFAULT_MSG + "雨天";
        }
        if("上海".equals(city)){
            return city + DEFAULT_MSG + "阴天";
        }
        return city + DEFAULT_MSG + "晴天";
    }
}
