package com.example.test.controller;

import com.example.test.bean.recipe;
import com.example.test.bean.recipeCollection;
import com.example.test.bean.recipeRecommend;
import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.service.intf.recipeRecommendService;
import com.example.test.service.intf.recipeService;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.example.test.utils.Imp.RespResult;
import net.sf.json.JSONArray;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/v2/recipe")
@RestController
public class RecipeController implements IPermission {
    @Resource
    envConfig config;
    @Resource
    recipeService recipeService;
    @Resource
    recipeRecommendService recipeRecommendService;
    @Override
    public boolean hasPermission(String username, int role, String URI) {
        return role == 2||role == 1;
    }
    @PostMapping("/get")
    /**
     * 用户可以访问
     * {
     *   "keyWord":"xxx",
     *   "name":true,
     *   "raw":true,
     *   "pageCount":10,
     *   "pageIndex":1
     * }
     */
    public RespResult<?> get(@RequestBody Map<String, String>map, HttpSession session){
        RespResult<?> result;
        String keyWord = map.getOrDefault("keyWord","");
        String indexStr = map.getOrDefault("pageIndex","1");
        String pageCountStr = map.getOrDefault("pageCount","20");
        String name = map.get("name");
        String raw = map.get("raw");
        int index=1,count=20;
        try{
            index = Integer.parseInt(indexStr);
            if(index<1) index = 1;
            count = Integer.parseInt(pageCountStr);
            if(count<=0) count = 20;
            if (raw.equals("true")&&name.equals("false"))
            {
                List<recipe> lst = recipeService.query(keyWord,true,false,index,count);
                List lst1 = new ArrayList<>();
                for (recipe recipe : lst) {
                    Map<String,Object> map1 = new HashMap<>();
                    map1.put("id",recipe.getId());
                    map1.put("name",recipe.getName());
                    map1.put("cookMethod",recipe.getCookMethod());
                    map1.put("cookTime",recipe.getCookTime());
                    map1.put("calorie",recipe.getCalorie());
                    map1.put("carbohydrate",recipe.getCarbohydrate());
                    map1.put("protein",recipe.getProtein());
                    map1.put("fat",recipe.getFat());
                    map1.put("cholesterol",recipe.getCholesterol());
                    map1.put("dietaryFiber",recipe.getDietaryFiber());
                    map1.put("minerals",recipe.getMinerals());
                    map1.put("vitamin",recipe.getVitamin());
                    map1.put("others",recipe.getOthers());
                    map1.put("mainMaterials",recipe.getMainMaterials());
                    map1.put("accessories",recipe.getAccessories());
                    map1.put("notCalculated",recipe.getNotCalculated());
                    if (recipe.getImg()!=null&&!recipe.getImg().equals(""))
                        map1.put("img",JSONArray.fromObject(recipe.getImg()));
                    else map1.put("img",null);
                    lst1.add(map1);
                }
                result = new RespResult<>(BaseRespResultCode.OK,lst1, config.getEnv(), "");
            }
            else if (raw.equals("false")&&name.equals("true"))
            {
                List<recipe> lst = recipeService.query(keyWord,false,true,index,count);
                List lst1 = new ArrayList<>();
                for (recipe recipe : lst) {
                    Map<String,Object> map1 = new HashMap<>();
                    map1.put("id",recipe.getId());
                    map1.put("name",recipe.getName());
                    map1.put("cookMethod",recipe.getCookMethod());
                    map1.put("cookTime",recipe.getCookTime());
                    map1.put("calorie",recipe.getCalorie());
                    map1.put("carbohydrate",recipe.getCarbohydrate());
                    map1.put("protein",recipe.getProtein());
                    map1.put("fat",recipe.getFat());
                    map1.put("cholesterol",recipe.getCholesterol());
                    map1.put("dietaryFiber",recipe.getDietaryFiber());
                    map1.put("minerals",recipe.getMinerals());
                    map1.put("vitamin",recipe.getVitamin());
                    map1.put("others",recipe.getOthers());
                    map1.put("mainMaterials",recipe.getMainMaterials());
                    map1.put("accessories",recipe.getAccessories());
                    map1.put("notCalculated",recipe.getNotCalculated());
                    if (recipe.getImg()!=null&&!recipe.getImg().equals(""))
                        map1.put("img",JSONArray.fromObject(recipe.getImg()));
                    else map1.put("img",null);
                    lst1.add(map1);
                }
                result = new RespResult<>(BaseRespResultCode.OK,lst1, config.getEnv(), "");
            }
            else if (raw.equals("true")&&name.equals("true"))
            {
                List<recipe> lst = recipeService.query(keyWord,true,true,index,count);
                List lst1 = new ArrayList<>();
                for (recipe recipe : lst) {
                    Map<String,Object> map1 = new HashMap<>();
                    map1.put("id",recipe.getId());
                    map1.put("name",recipe.getName());
                    map1.put("cookMethod",recipe.getCookMethod());
                    map1.put("cookTime",recipe.getCookTime());
                    map1.put("calorie",recipe.getCalorie());
                    map1.put("carbohydrate",recipe.getCarbohydrate());
                    map1.put("protein",recipe.getProtein());
                    map1.put("fat",recipe.getFat());
                    map1.put("cholesterol",recipe.getCholesterol());
                    map1.put("dietaryFiber",recipe.getDietaryFiber());
                    map1.put("minerals",recipe.getMinerals());
                    map1.put("vitamin",recipe.getVitamin());
                    map1.put("others",recipe.getOthers());
                    map1.put("mainMaterials",recipe.getMainMaterials());
                    map1.put("accessories",recipe.getAccessories());
                    map1.put("notCalculated",recipe.getNotCalculated());
                    if (recipe.getImg()!=null&&!recipe.getImg().equals(""))
                        map1.put("img",JSONArray.fromObject(recipe.getImg()));
                    else map1.put("img",null);
                    lst1.add(map1);
                }
                result = new RespResult<>(BaseRespResultCode.OK,lst1, config.getEnv(), "");
            }
            else result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
        }catch (NumberFormatException e){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            result.setDetailMessage(e.getMessage());
        }
        return result;
    }
    @PostMapping("/collect")
    public RespResult<?>collect(@RequestBody Map<String,String>map,HttpSession session){
        RespResult<?> result;
        String UID = (String) session.getAttribute("UID");
        int dishId = Integer.parseInt(map.get("dishId"));
        int success = recipeService.insert(UID,dishId, LocalDateTime.now());
        result = new RespResult<>(BaseRespResultCode.OK,success,config.getEnv(),"");
        return result;
    }
    @PostMapping("/cancelCollect")
    public RespResult<?>cancel(@RequestBody Map<String,String>map,HttpSession session){
        RespResult<?> result;
        String UID = (String) session.getAttribute("UID");
        int dishId = Integer.parseInt(map.get("dishId"));
        int success = recipeService.delete(UID,dishId);
        result = new RespResult<>(BaseRespResultCode.OK,success,config.getEnv(),"");
        return result;
    }
    @PostMapping("/getCollection")
    public RespResult<?>getCollection(HttpSession session){
        RespResult<?> result;
        String UID = (String) session.getAttribute("UID");
        List<recipeCollection> lst = recipeService.queryCollection(UID);
        List lst1 = new ArrayList<>();
        for (recipeCollection recipeCollection : lst) {
            Map<String,Object>map = new HashMap<>();
            Map<String,Object> map1 = new HashMap<>();
            map1.put("id",recipeCollection.getDish().getId());
            map1.put("name",recipeCollection.getDish().getName());
            map1.put("cookMethod",recipeCollection.getDish().getCookMethod());
            map1.put("cookTime",recipeCollection.getDish().getCookTime());
            map1.put("calorie",recipeCollection.getDish().getCalorie());
            map1.put("carbohydrate",recipeCollection.getDish().getCarbohydrate());
            map1.put("protein",recipeCollection.getDish().getProtein());
            map1.put("fat",recipeCollection.getDish().getFat());
            map1.put("cholesterol",recipeCollection.getDish().getCholesterol());
            map1.put("dietaryFiber",recipeCollection.getDish().getDietaryFiber());
            map1.put("minerals",recipeCollection.getDish().getMinerals());
            map1.put("vitamin",recipeCollection.getDish().getVitamin());
            map1.put("others",recipeCollection.getDish().getOthers());
            map1.put("mainMaterials",recipeCollection.getDish().getMainMaterials());
            map1.put("accessories",recipeCollection.getDish().getAccessories());
            map1.put("notCalculated",recipeCollection.getDish().getNotCalculated());
            if (recipeCollection.getDish().getImg()!=null&&!recipeCollection.getDish().getImg().equals(""))
                map1.put("img",JSONArray.fromObject(recipeCollection.getDish().getImg()));
            else map1.put("img",null);
            map.put("dish",map1);
            map.put("time",recipeCollection.getTime());
            map.put("uid",recipeCollection.getUID());
            lst1.add(map);
        }
        result = new RespResult<>(BaseRespResultCode.OK,lst1,config.getEnv(),"");
        return result;
    }

    @PostMapping("/getRecommend")
    public RespResult<?> getRecommend(HttpSession session){
        RespResult<?> result;
        String UID = (String) session.getAttribute("UID");
        recipeRecommend rr = null;
        try {
            rr = recipeRecommendService.getRecommend(UID);
            result = new RespResult<>(BaseRespResultCode.OK,rr, config.getEnv(), "");
        } catch (IOException | InterruptedException e) {
            result = new RespResult<>(1001001,"生成失败",e.getMessage(),null,config.getEnv(),"");
        }
        return result;
    }
    @PostMapping("/changeRecommend")
    public RespResult<?> changeRecommend(HttpSession session){
        RespResult<?> result;
        String UID = (String) session.getAttribute("UID");
        recipeRecommend rr = null;
        try {
            rr = recipeRecommendService.changeRecommend(UID);
            result = new RespResult<>(BaseRespResultCode.OK,rr, config.getEnv(), "");
        } catch (IOException | InterruptedException e) {
            result = new RespResult<>(1001001,"生成失败",e.getMessage(),null,config.getEnv(),"");
        }
        return result;
    }
    @PostMapping("/likeRecommend")
    public RespResult<?> likeRecommend(HttpSession session){
        RespResult<?> result;
        String UID = (String) session.getAttribute("UID");
        recipeRecommend rr = null;
        recipeRecommendService.likeRecommend(UID);
        result = new RespResult<>(BaseRespResultCode.OK,"",config.getEnv(),"");
        return result;
    }

}
