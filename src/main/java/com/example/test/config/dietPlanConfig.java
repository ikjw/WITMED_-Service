package com.example.test.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dietplan")
public class dietPlanConfig {
    private String pythonExe;//对应环境的python.exe地址
    private String recipeScript;//获取菜谱的脚本地址
    private String dietScript;//获取推荐的脚本地址

    public String getPythonExe() {
        return pythonExe;
    }

    public void setPythonExe(String pythonExe) {
        this.pythonExe = pythonExe;
    }

    public String getRecipeScript() {
        return recipeScript;
    }

    public void setRecipeScript(String recipeScript) {
        this.recipeScript = recipeScript;
    }

    public String getDietScript() {
        return dietScript;
    }

    public void setDietScript(String dietScript) {
        this.dietScript = dietScript;
    }
}
