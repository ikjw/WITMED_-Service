package com.example.test.service.Imp;

import com.example.test.bean.dietPlan;
import com.example.test.bean.recipe;
import com.example.test.bean.recipeRecommend;
import com.example.test.config.dietPlanConfig;
import com.example.test.dao.recipeRecommendDao;
import com.example.test.service.intf.dietPlanService;
import com.example.test.service.intf.recipeRecommendService;
import com.example.test.service.intf.recipeService;
import com.example.test.utils.CheckPreCondition;
import com.example.test.utils.fastJsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class recipeRecommendImp implements recipeRecommendService {
    @Resource
    dietPlanConfig config;
    @Resource
    dietPlanService dietPlanService;
    @Resource
    recipeService recipeService;
    @Resource
    recipeRecommendDao dao;
    static float[][] changeTable = new float[][]
            {    //蛋白质，脂肪，糖类
                    {2,0,20},//主食
                    {5,0,17},//蔬菜
                    {1,0,21},//水果
                    {9,6,0},//肉蛋
                    {9,4,4},//豆
                    {5,5,6},//奶
                    {4,7,2},//坚果
                    {0,10,0}//油
            };
    @Override
    public recipeRecommend getRecommend(String UID) throws IOException, InterruptedException {
        CheckPreCondition.notNull(UID);
        dietPlan plan = dietPlanService.queryRecent(UID);
        recipeRecommend rr = dao.getByDietPlan(plan.getId());
        if(rr == null){
            float[] tmp = new float[8];
            if(plan == null){
                //主食，蔬菜，水果，肉蛋，豆，奶，坚果，油
                tmp = new float[]{9,1,1,3,1,2,1,1};
            }else{
                tmp = new float[]{plan.getMainFood(),plan.getVegetables(),plan.getFruits(),plan.getMeat_egg(),
                        plan.getSoybeans(),plan.getDairy(),plan.getNuts(),plan.getOils()};
            }
            float[] nutr = new float[]{0,0,0};
            for(int i=0;i< tmp.length;i++){
                for(int j= 0;j<nutr.length;j++){
                    nutr[j] += tmp[i]*changeTable[i][j];
                }
            }
            rr = algorithm(nutr);
            rr.setDietPlanId(plan.getId());
            if(rr!=null)
                dao.insert(rr);
        }
        return rr;
    }
    private recipeRecommend algorithm(float[] nutr) throws IOException, InterruptedException {
        recipeRecommend rr = null;
        Process proc;
        String[] args1=new String[]{
                config.getPythonExe(),
                config.getRecipeScript(),
                String.valueOf(nutr[0]),
                String.valueOf(nutr[1]),
                String.valueOf(nutr[2])};
        proc=Runtime.getRuntime().exec(args1);
        BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = in.readLine()) != null) {
            builder.append(line);
        }
        String tmp = builder.toString();
        rr = parserFromJson(tmp);
        in.close();
        proc.waitFor();
        return rr;
    }
    @SneakyThrows
    @Override
    public void likeRecommend(String UID) {
        CheckPreCondition.notNull(UID);
        recipeRecommend rr = this.getRecommend(UID);
        rr.setState(2);
        dao.update(rr);
    }

    @Override
    public recipeRecommend changeRecommend(String UID) throws IOException, InterruptedException {
        CheckPreCondition.notNull(UID);
        recipeRecommend rr = this.getRecommend(UID);
        if(rr.getState() == 1){
            rr.setState(3);
            rr.setChangeTime(LocalDateTime.now());
        }else if(rr.getState() == 2){
            rr.setState(4);
            rr.setChangeTime(LocalDateTime.now());
        }
        dao.update(rr);
        return this.getRecommend(UID);
    }

    private recipeRecommend parserFromJson(String jsonStr) throws JsonProcessingException {
        recipeRecommend rr = new recipeRecommend();
        JSONObject json = fastJsonUtils.jsonStrToObject(jsonStr, JSONObject.class);
        JSONArray bf = json.getJSONArray("breakfast");
        JSONArray lc = json.getJSONArray("lunch");
        JSONArray dn = json.getJSONArray("dinner");
        List<recipe> breakfast = new ArrayList<>();
        List<recipe> lunch = new ArrayList<>();
        List<recipe> dinner = new ArrayList<>();
        for(int i=0;i<bf.size();i++){
            recipe rc = recipeService.queryById(bf.getInt(i));
            if(rc!=null){
                breakfast.add(rc);
            }
        }
        for(int i=0;i<lc.size();i++){
            recipe rc = recipeService.queryById(lc.getInt(i));
            if(rc!=null){
                lunch.add(rc);
            }
        }
        for(int i=0;i<dn.size();i++){
            recipe rc = recipeService.queryById(dn.getInt(i));
            if(rc!=null){
                dinner.add(rc);
            }
        }
        rr.setBreakfast(breakfast);
        rr.setLunch(lunch);
        rr.setDinner(dinner);
        rr.setState(1);
        rr.setCreateTime(LocalDateTime.now());
        return rr;
    }
}
