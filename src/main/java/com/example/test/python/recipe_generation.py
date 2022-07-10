from recipesnew import get_recipe
# from recipe import get_recipe
import random
import time
import read_data

class diet_plan_generation():
    def __init__(self, patient_condition, bias_threshold=0):
        # patient_condition, list of numbers
        # bias_threshold , int

        self.recipe_type_to_id = {'主食': 0, '薯类': 1, '奶类': 2, '蛋类': 3, '肉类': 4, '豆制品': 5, '蔬菜': 6}
        self.food_units = [25, 25, 250, 60, 50, 100, 50] # 肉类由25改为50，奶类由50改为160/250
        # self.food_units0 = [25, 25, 50, 60, 25, 25, 50]

        # Patient_condition: 每日7类食物总克数
        self.patient_condition = patient_condition
        self.bias_threshold = bias_threshold
        # 食材份数三餐分配结果
        self.general_diet_plan = [[0 for j in range(0,len(patient_condition))] for i in range(0,3)]
        # 最终三餐食谱集合
        self.recipe_set = {0:[], 1:[],2:[]}
        self.recipe_nutrition = [0 for i in range(len(patient_condition))]

        self.allday_energy = 0
        self.allday_protein = 0
        self.allday_fat = 0
        self.allday_carbohydrate = 0
        self.allday_cholesterol = 0

    def get_general_diet_plan(self):
        # 分配原则:
        #   能量，主食，蔬菜三餐分配比例 : 1:2:2
        #   午餐至少250g蔬菜
        #   肉，蛋，豆，分配原则:
        #       肉,蛋,豆不能放到一顿
        #       肉分开两顿(一般午餐,晚餐),鱼只能有一顿
        #       蛋,豆可以放到一顿
        #       蛋白质也进行122分配

        #   注意：
        #       各种食物种类分量是交换份的整数倍：
        #           各类食物交换份：
        #           主食：25g，薯类： 25g， 奶类：250g（一盒牛奶的重量），豆制品：25g，蛋类：60g（一个鸡蛋的重量），鱼肉类：50g，蔬菜类：500g起步，50g为一个能量段
        #       总量不能有误差，比例可以有误差

        # 按照分配原则,随机分配
        # 保存结果至 self.general_diet_plan

        # 主食类，蔬菜类：
        #   1. 重量处理成交换份
        #   2. 尽量按照1：2：2，直接固定分配
        for i in range(0, len(self.patient_condition)):
            unit = self.patient_condition[i] / self.food_units[i]
            if i in [self.recipe_type_to_id['主食'], self.recipe_type_to_id['蔬菜']]:
                distribution = 0
                for j in [3, 4, 5, 7, 11, 13]:
                    if unit % j == 0:
                        if j == 3:
                            # 早午晚： 1：1：1
                            distribution = 1
                            self.general_diet_plan[0][i] = (unit/3) * self.food_units[i]
                            self.general_diet_plan[1][i] = (unit/3) * self.food_units[i]
                            self.general_diet_plan[2][i] = (unit/3) * self.food_units[i]
                        elif j == 4:
                            # 早午晚： 1：2：1
                            distribution = 1
                            self.general_diet_plan[0][i] = (unit/4) * self.food_units[i]
                            self.general_diet_plan[1][i] = 2 * (unit/4) * self.food_units[i]
                            self.general_diet_plan[2][i] = 1 * (unit/4) * self.food_units[i]
                        elif j == 5:
                            # 早午晚： 1：2：2
                            distribution = 1
                            self.general_diet_plan[0][i] = (unit/5) * self.food_units[i]
                            self.general_diet_plan[1][i] = 2 * (unit/5) * self.food_units[i]
                            self.general_diet_plan[2][i] = 2 * (unit/5) * self.food_units[i]
                        elif j == 7:
                            # 早午晚： 1：3：3
                            distribution = 1
                            self.general_diet_plan[0][i] = 1 * (unit/7) * self.food_units[i]
                            self.general_diet_plan[1][i] = 3 * (unit/7) * self.food_units[i]
                            self.general_diet_plan[2][i] = 3 * (unit/7) * self.food_units[i]
                        elif j == 11:
                            # 早午晚： 3：4：4
                            distribution = 1
                            self.general_diet_plan[0][i] = (unit / 11) * 3 * self.food_units[i]
                            self.general_diet_plan[1][i] = (unit / 11) * 4 * self.food_units[i]
                            self.general_diet_plan[2][i] = (unit / 11) * 4 * self.food_units[i]
                        elif j == 13:
                            # 早午晚： 3：5：5
                            distribution = 1
                            self.general_diet_plan[0][i] = (unit / 13) * 3 * self.food_units[i]
                            self.general_diet_plan[1][i] = (unit / 13) * 5 * self.food_units[i]
                            self.general_diet_plan[2][i] = (unit / 13) * 5 * self.food_units[i]

                if distribution == 0:
                    print(i,unit)
                    raise ValueError("分配异常")

            elif i == self.recipe_type_to_id['肉类']:
                # 鱼肉类：
                #   1. 是否吃鱼（每周提供2-3次）
                #       若吃，午餐吃或晚餐吃（随机）
                #   2. 否则，早中晚三选二，按1：1分配。
                #   3. 重量处理成交换份,1：1分配到午餐，晚餐
                #   4. 若1：1分配，不是鱼肉类交换份的整数倍，则按照1：2：2分配
                self.recipe_type_to_id['肉类'] = 2
                # have_fish = (random.choice([1,2,3,4,5,6,7]) <= 3)
                have_fish = random.choice([0,1])
                unit = self.patient_condition[i] / self.food_units[i]
                if have_fish:
                    fish_in_lunch = random.choice([0,1])
                    if fish_in_lunch:
                        # 将鱼分配到午餐
                        self.general_diet_plan[1][i] = self.patient_condition[i]  #TODO:
                    else:
                        # 将鱼分配到晚餐
                        self.general_diet_plan[2][i] = self.patient_condition[i]
                else:
                    # 重量处理成交换份
                    distribution = 0
                    for j in [2, 3, 5]:
                        if unit % j == 0:
                            if j == 2:
                                # 早午晚： 0：1：1
                                distribution = 1
                                self.general_diet_plan[1][i] = (unit / 2) * self.food_units[i]
                                self.general_diet_plan[2][i] = (unit / 2) * self.food_units[i]
                            elif j == 3:
                                # 早午晚： 1：1：1
                                distribution = 1
                                self.general_diet_plan[0][i] = (unit / 3) * self.food_units[i]
                                self.general_diet_plan[1][i] = (unit / 3) * self.food_units[i]
                                self.general_diet_plan[2][i] = (unit / 3) * self.food_units[i]
                            elif j == 5:
                                # 早午晚： 1：2：2
                                distribution = 1
                                self.general_diet_plan[0][i] = (unit / 5) * self.food_units[i]
                                self.general_diet_plan[1][i] = 2 * (unit / 5) * self.food_units[i]
                                self.general_diet_plan[2][i] = 2 * (unit / 5) * self.food_units[i]

                    if distribution == 0:
                        raise ValueError("分配异常")

            elif i == self.recipe_type_to_id['奶类']:
                # 奶类：
                # 随机作为早餐或加餐整体添加
                one_more_meal = 0
                break_milk = random.choice([0,1])
                if break_milk:
                    self.general_diet_plan[0][i] = self.patient_condition[i]
                else:
                    one_more_meal = 1

            elif i == self.recipe_type_to_id['蛋类']:
                # 蛋类，豆制品：
                # 1. 蛋白质按照 1：2：2 或 1：1：1 分配
                # 2. 蛋类（每次60g）进行填充（填充后，未满足蛋白质小于5g）
                # 3. 最后填充豆制品
                #
                # 注意：
                #   肉蛋豆不能同时在一顿内（理论上，第2步已作限制，不会出现）
                #   蛋白质分配计算
                #       谷薯类（25g含有2g），蔬菜类（500g含有5g），豆制品（25g含有9g），奶类（160g含有5g），肉蛋类（50g含有9g）
                bean_meal = [0, 1, 2]
                protein_contain = [2/25, 2/25, 5/160, 9/50, 9/50, 9/25, 1/100]
                total_protein = sum([self.patient_condition[j] * protein_contain[j] for j in range(len(self.patient_condition))])
                meal = [0,1,2]
                random.shuffle(meal)
                for meal_type in meal:
                    meal_protein = sum([self.general_diet_plan[meal_type][j] * protein_contain[j] for j in range(len(self.patient_condition))])
                    if (meal_protein + 60 * (9/50)) <= (total_protein / 3):
                        self.general_diet_plan[meal_type][i] = 60  #TODO:
                        del bean_meal[bean_meal.index(meal_type)]
                        break

            elif i == self.recipe_type_to_id['豆制品']:
                while(True):
                    meal_type = random.choice(bean_meal)

                    one_meal = random.choice([0, 1])
                    meal_protein = sum([self.general_diet_plan[meal_type][j] * protein_contain[j] for j in range(len(self.patient_condition))])
                    if one_meal:
                        if (meal_protein + self.patient_condition[i] * (9/25)) <= (total_protein / 3):
                            self.general_diet_plan[meal_type][i] = self.patient_condition[i]
                            break
                        elif meal_type == bean_meal[-1]:
                            continue
                    else:
                        units = self.patient_condition[i]/25
                        if units % 2 == 0:
                            if (meal_protein + units/2 * (9/25)) <= (total_protein / 3):
                                self.general_diet_plan[meal_type][i] = self.patient_condition[i]/2
                                self.general_diet_plan[bean_meal[bean_meal.index(meal_type) - 1]][i] = self.patient_condition[i]/2  # TODO:
                                break
                        elif units % 3 == 0:
                            if (meal_protein + units/3 * 2 * (9/25)) <= (total_protein / 3):
                                self.general_diet_plan[meal_type][i] = self.patient_condition[i]/3 * 2
                                self.general_diet_plan[bean_meal[bean_meal.index(meal_type) - 1]][i] = self.patient_condition[i]/3
                                break
                        elif units % 5 == 0:
                            if (meal_protein + units/5 * 3 * (9/25)) <= (total_protein / 3):
                                self.general_diet_plan[meal_type][i] = self.patient_condition[i]/5 * 3
                                self.general_diet_plan[bean_meal[bean_meal.index(meal_type) - 1]][i] = self.patient_condition[i]/5 * 2
                                break
                        else:
                            raise ValueError("豆制品",units)
        # 加餐只计算奶类
        if one_more_meal:
            self.general_diet_plan.append([0, 0, self.patient_condition[2], 0, 0, 0, 0])
            self.recipe_set[3] =[]


    def get_meal_recipes(self, num_recipes = 2, meal_type = 0, labels = [None, None, None, None, None, None, None, None, None, None, None]):
        # 通过 valid_meal(), 验证菜谱的合理性
        # 合理，输出
        # 不合理，再次组合菜谱
        # meal_type: 早中晚餐
        # while(self.valid_meal(meal_type)):
            # 组合菜谱
        self.construct_meal_recipes(num_recipes, meal_type,labels = labels)
        return self.recipe_set

    def construct_meal_recipes(self, max_num_recipes, meal_type, labels = [None, None, None, None, None, None, None, None, None, None, None]):
        # 参数：最大菜谱数，进餐类别
        recipe_in_meal = []
        # 组合菜谱的原则:
        #   通过 get_DBrecipes() 获取菜谱
        #   基本按照 self.general_diet_plan
        #   食材不重复
        # 结果写入 recipe_set
        food_in_meal = []
        # 奶类，直接规则处理，加到早餐或加餐中
        for meal in range(len(self.general_diet_plan)):
            if meal_type == 0:
                if self.general_diet_plan[meal][self.recipe_type_to_id['奶类']] > 0:
                    self.recipe_nutrition[self.recipe_type_to_id['奶类']] = self.general_diet_plan[meal][
                        self.recipe_type_to_id['奶类']]
                    if random.choice([0, 1]):
                        milk_type = '牛奶'
                    else:
                        milk_type = '酸奶'

                    self.recipe_set[meal].append({'菜名': milk_type, '主料': {
                        milk_type: str(self.general_diet_plan[meal][self.recipe_type_to_id['奶类']]) + 'g'}})
                    self.recipe_nutrition[self.recipe_type_to_id['奶类']] = self.general_diet_plan[meal][
                        self.recipe_type_to_id['奶类']]
                    break

        # 主食随机分配
        recipe = {}
        recipe['菜名'] = random.choice(['米饭','面条','馒头','花卷'])
        recipe['一人使用份量'] = {}
        recipe['一人使用份量'][recipe['菜名']] = self.general_diet_plan[meal_type][0]

        # 蛋类食谱
        # recipe_egg={}
        # recipe_egg['菜名']='鸡蛋'
        # recipe_egg['一人使用份量']={}
        # recipe_egg['一人使用份量']['鸡蛋']=self.food_units[self.recipe_type_to_id['蛋类']]
        # print(recipe['菜名'], recipe['主料'][recipe['菜名']])
        self.recipe_set[meal_type].append(recipe)
        now_condition = [self.general_diet_plan[meal_type][0],0,0,0,0,0,0]
        num_recipes = 1
        while(num_recipes <= max_num_recipes):
            if(meal_type==0):
                break
            for recipe_type in ['肉类', '豆制品', '蔬菜', '蛋类','薯类']:
                # if num_recipes > max_num_recipes:
                #     if recipe_type != '蔬菜' or recipe_type != '蛋类':
                #         break
                # 薯类出现较少，亦可通过规则处理

                finish = 0
                while(0 == finish):
                    # if recipe_type == "蛋类":
                    #     print(self.general_diet_plan,meal_type)
                    # 从数据库中采集20条菜谱
                    recipes = get_recipe(recipes_type=[recipe_type], not_in_mainfood=food_in_meal, labels = labels, num_recipe=20)

                    for recipe in recipes:
                        if "鱼类" in recipe or "肉类" in recipe:
                            # “面条鱼”污染鱼类标签，故排除
                            if "鱼类" in recipe:
                                if "面条" in " ".join(list(recipe["鱼类"].keys())):
                                    continue
                            if "肉类" in recipe:
                                if "面条" in " ".join(list(recipe["肉类"].keys())):
                                    continue
                        temp_s=recipe['菜名']+" ".join(list(recipe['主料'].keys()))
                        if "主食" not in recipe:
                            if "面" in temp_s or "糕" in temp_s or "饺" in temp_s or "饼" in temp_s or "吐司" in temp_s or "拉皮" in temp_s:
                                continue
                            if "包" in recipe['菜名'] and "蛋" not in recipe['菜名']:
                                continue
                        if "蔬菜" in recipe and "薯" in " ".join(list(recipe["蔬菜"].keys())):
                            continue
                        if " " in recipe['菜名']:
                            recipe["菜名"]=recipe['菜名'].split(" ")[-1]
                        # if meal_type==0:
                        #     if self.general_diet_plan[0][2]>0:
                        #         if "豆浆" in recipe["菜名"] or "汤" in recipe["菜名"] or "粥" in recipe["菜名"]:
                        #             continue
                        liquid=["豆浆","奶","粥","汤"]
                        foodname_meal=[item["菜名"] for item in self.recipe_set[meal_type]]
                        namerepeat_flag=0
                        for li in range(len(liquid)):
                            if liquid[li] in " ".join(foodname_meal):
                                for lj in range(len(liquid)):
                                    if liquid[lj] in recipe["菜名"]:
                                        namerepeat_flag=1
                                        break
                        if namerepeat_flag==1:
                            continue
                        # 三餐菜品汇总
                        for temp in self.recipe_set[0] + self.recipe_set[1] + self.recipe_set[2]:
                            recipe_in_meal.append(temp['菜名'])
                        # 排除油炸食品
                        if (recipe['菜名'][-2:] in (' '.join(recipe_in_meal))) | ('炸' in recipe['菜名']):
                            continue

                        recipe_rate = (self.general_diet_plan[meal_type][self.recipe_type_to_id[recipe_type]] - \
                                       now_condition[self.recipe_type_to_id[recipe_type]]) / float(recipe[recipe_type]['总量'])
                        if recipe_rate <= 0.0:
                            continue

                        temp_condition, recipe = self.add_recipe_to_meal(recipe, recipe_rate, now_condition, meal_type)
                        # if recipe_type=="蛋类":
                        #     print(now_condition)
                        if temp_condition != []:
                            num_recipes += 1
                            now_condition = temp_condition
                            for food in recipe['主料'].keys():
                                food_in_meal.append(food.replace(',',''))
                            finish = 1
                            # if self.general_diet_plan[meal_type][self.recipe_type_to_id["蛋类"]]-now_condition
                            # break
                    # 如果该类食物份量已经配足(与 self.general_diet_plan 相差小于半个交换份)，则分配下一类食物
                    # 若与 self.general_diet_plan 相差大于一个交换份，继续分配
                    recipe_id = self.recipe_type_to_id[recipe_type]
                    if abs(now_condition[recipe_id] - self.general_diet_plan[meal_type][recipe_id]) < self.food_units[recipe_id] / 2:
                        # print(recipe_id)
                        break
                    else:
                        continue
                    # elif abs(now_condition[recipe_id] - self.general_diet_plan[meal_type][recipe_id]) < self.food_units[recipe_id] * 2:
                    #     # print("  ",recipe_id)
                    #     continue
        self.recipe_nutrition = [now_condition[nu] + self.recipe_nutrition[nu] for nu in range(len(self.recipe_nutrition))]

    def add_recipe_to_meal(self, recipe, recipe_rate, now_condition, meal_type):
        # 按一定比例修改食谱
        staple_food = recipe.get('主食', {}).get('总量', 0) * recipe_rate
        tubers = recipe.get('薯类', {}).get('总量', 0) * recipe_rate
        mlik = recipe.get('奶类', {}).get('总量', 0) * recipe_rate
        egg = recipe.get('蛋类', {}).get('总量', 0) * recipe_rate
        meat = recipe.get('肉类', {}).get('总量', 0) * recipe_rate
        bean = recipe.get('豆制品', {}).get('总量', 0) * recipe_rate
        vegetable = recipe.get('蔬菜', {}).get('总量', 0) * recipe_rate

        # 将修改后食谱加入到这一餐之后，是否超标
        recipe_condition = [staple_food, tubers, mlik,egg, meat, bean, vegetable]
        half_units = [fu/2 for fu in self.food_units]  #TODO:
        compare = [1 if (recipe_condition[i] + now_condition[i] - self.general_diet_plan[meal_type][i]) < half_units[i] else 0
                    for i in range(0,len(recipe_condition))]

        # if all(compare):
        #     print(self.general_diet_plan,meal_type,now_condition,"----------",recipe_condition,"```````````",recipe_condition[3] + now_condition[3] - self.general_diet_plan[meal_type][3])
        # 如果符合，返回最新的 now_condition 与 recipe，并把recipe加入到 self.recipe_set
        if all(compare):
            # print(recipe["营养成分"])
            # if recipe["营养成分"].get("热量(千卡)",0):
            #     self.allday_energy += recipe["营养成分"]["热量(千卡)"] * recipe_rate
            # else:
            #     self.allday_energy += recipe["营养成分"]["热量(大卡)"] * recipe_rate
            # self.allday_carbohydrate += recipe["营养成分"]["碳水化合物(克)"] * recipe_rate
            # self.allday_protein += recipe["营养成分"]["蛋白质(克)"] * recipe_rate
            # self.allday_fat += recipe["营养成分"]["脂肪(克)"] * recipe_rate
            # self.allday_cholesterol += recipe["营养成分"][ "胆固醇(毫克)"] * recipe_rate

            # recipe_rate = round(recipe_rate, 0)
            if recipe_rate != 0:
                recipe['合理食用人数'] = str(recipe_rate) + '-' + str(recipe_rate + 1)
            else:
                recipe['合理食用人数'] = 1

            main_food_for_person = {}
            for food in recipe['主料'].keys():
                main_food_for_person[food] = round(recipe['主料'][food] * recipe_rate, 0) #if round(recipe['主料'][food] * recipe_rate / 50, 0) * 50 != 0 else '适量'

            recipe['一人使用份量'] = main_food_for_person
            # print(main_food_for_person)
            # exit()
            self.recipe_set[meal_type].append(recipe)
            return [recipe_condition[i] + now_condition[i] for i in range(0,len(recipe_condition))], recipe
        else:
            return [], []

def main(input):
    # input：每日7类食物克数
    diet_plan = diet_plan_generation(input)
    diet_plan.get_general_diet_plan() # 将食材份数分配至三餐
    #print('总克数：',input,'\n','三餐克数：',diet_plan.general_diet_plan) # general_diet_plan size: (4, 7)

    # print(diet_plan.general_diet_plan[0][0])
    # print(diet_plan.general_diet_plan[1][0])
    # print(diet_plan.general_diet_plan[2][0])

    # 组合三餐菜谱
    diet_plan.get_meal_recipes(meal_type=0, num_recipes=2,labels = {'场景':'早餐'} )  #TODO:labels
    diet_plan.get_meal_recipes(meal_type=1, num_recipes=2, labels=None)  #, labels = {'菜系':'东北菜'}
    diet_plan.get_meal_recipes(meal_type=2, num_recipes=2, labels=None)  #, labels = {'菜系':'东北菜'}

    meal_type = {0:'breakfirst', 1:'lunch', 2:'dinner',3:'extra'}
    output_text = '{'
    tt_ls=[]
    tt_dict = {}
    for meal in diet_plan.recipe_set:
        #output_text += meal_type[meal] + ':' #\n
        for j in diet_plan.recipe_set[meal]:
            if j.get('一人使用份量'):
                detail = '('
                for kk in j.get('一人使用份量'):
                    detail += kk + str(j.get('一人使用份量')[kk]) + 'g, '
                detail = detail[:-2] + ')'
            else:
                detail = '(' + j.get('菜名') + str(diet_plan.patient_condition[2]) +'g)'
            # output_text += "\t" if output_text[-1]==":" else "\t\t"
            # output_text += j.get('菜名') + '\t' + detail + '\n'
            # output_text += j.get('菜名')+ detail + '|'
            #tt_ls.append(j.get('菜名') + detail)
            tt_ls.append({"name":j.get('菜名'),"detail":detail})
        #output_text += str(tt_ls) + "\n"
        tt_dict[meal_type[meal]] = tt_ls
        tt_ls = []
        if meal == 3:
            tt_ls.append({"name":"水果","detail":"(水果250g)"})
            tt_ls.append({"name": "坚果", "detail": "((坚果15g))"})
            #output_text += '水果(水果250g)|坚果(坚果15g)|'
        #output_text += '\n' #\n
        # print(tt_ls)
    output_text+=str(tt_dict)
    diet_plan.recipe_nutrition[0] = diet_plan.patient_condition[0]

    energy = diet_plan.patient_condition[0] * 90 / 25 + diet_plan.patient_condition[1] * 90 / 25 + \
             diet_plan.patient_condition[2] * 90 / 160 + diet_plan.patient_condition[3] * 90 / 70 + \
             diet_plan.patient_condition[4] * 90 / 70 + diet_plan.patient_condition[5] * 90 / 100 + \
             diet_plan.patient_condition[6] * 90 / 500 + 3 * 90

    # output_text +='医生建议:\n' + '能量：'+ str(round(energy,0)) +';主食：'+ str(round(diet_plan.patient_condition[0],0)) + 'g;薯类：'+ str(round(diet_plan.patient_condition[1],0))\
    #             + 'g;奶类：' + str(round(diet_plan.patient_condition[2],0)) + 'g;蛋类：' + str(round(diet_plan.patient_condition[3],0)) \
    #             + 'g;肉类：' + str(round(diet_plan.patient_condition[4],0)) + 'g;豆制品：' + str(round(diet_plan.patient_condition[5],0)) \
    #             + 'g;蔬菜：' + str(round(diet_plan.patient_condition[6],0)) \
    #             + 'g;水果：' + str(250) + 'g:坚果：' + str(15) + 'g'
    detail = {"mainFood": round(diet_plan.patient_condition[0]),
              "potato": round(diet_plan.patient_condition[1]),
              "milk": round(diet_plan.patient_condition[2]),
              "egg": round(diet_plan.patient_condition[3]),
              "meat": round(diet_plan.patient_condition[4]),
              "bean": round(diet_plan.patient_condition[5]),
              "vegetables": round(diet_plan.patient_condition[6]),
              "fruit": 250,
              "nut": 15
              }
    tt_dict["expected"] = {"total": round(energy), "detail": detail}
    #output_text += "医生建议:" + str(temp)
    energy = diet_plan.recipe_nutrition[0] * 90 / 25 + diet_plan.recipe_nutrition[1] * 90 / 25 + \
             diet_plan.recipe_nutrition[2] * 90 / 160 + diet_plan.recipe_nutrition[3] * 90 / 70 + \
             diet_plan.recipe_nutrition[4] * 90 / 70 + diet_plan.recipe_nutrition[5] * 90 / 100 + \
             diet_plan.recipe_nutrition[6] * 90 / 500 + 3 * 90

    # output_text +='\n本食谱包含：\n' + '能量：'+ str(round(energy,0)) +';
    # 主食：'+ str(round(diet_plan.recipe_nutrition[0],0)) + 'g;
    # 薯类：'+ str(round(diet_plan.recipe_nutrition[1],0))\
    #             + 'g;奶类：' + str(round(diet_plan.recipe_nutrition[2],0)) + 'g;
    #             蛋类：' + str(round(diet_plan.recipe_nutrition[3],0)) \
    #             + 'g;肉类：' + str(round(diet_plan.recipe_nutrition[4],0)) + 'g;
    #             豆制品：' + str(round(diet_plan.recipe_nutrition[5],0)) \
    #             + 'g;蔬菜：' + str(round(diet_plan.recipe_nutrition[6],0)) \
    #             + 'g;水果：' + str(250) + 'g:坚果：' + str(15) + 'g'

    detail = {"mainFood": round(diet_plan.recipe_nutrition[0]),
              "potato": round(diet_plan.recipe_nutrition[1]),
              "milk": round(diet_plan.recipe_nutrition[2]),
              "egg": round(diet_plan.recipe_nutrition[3]),
              "meat": round(diet_plan.recipe_nutrition[4]),
              "bean": round(diet_plan.recipe_nutrition[5]),
              "vegetables": round(diet_plan.recipe_nutrition[6]),
              "fruit":250,
              "nut":15
              }
    tt_dict["actual"] = {"total": round(energy), "detail": detail}
    #output_text+= "本食谱包括:"+str(temp) + "\n"
    import json
    print(json.dumps(tt_dict,ensure_ascii=True))# + str(diet_plan.allday_energy) + '\n' + str(diet_plan.allday_carbohydrate) + '\n' + str(diet_plan.allday_protein) + '\n' + str(diet_plan.allday_fat) + '\n' + str(diet_plan.allday_cholesterol)
    # if diet_plan.recipe_nutrition[3]<1:
    #     print(diet_plan.general_diet_plan)
    #     exit()
import sys
# input = []
# for i in sys.argv[1].split(','):
#     input.append(float(i))
# # input = [350, 0, 250, 60, 100, 100,650]
# main(input)

# {'主食': 0, '薯类': 1, '奶类': 2, '蛋类': 3, '肉类': 4, '豆制品': 5, '蔬菜': 6}
# {主食0， 蔬菜1， 水果2， 肉蛋3， 豆制品4， 奶类5， 坚果6， 油7， 盐8}
# test_x = [[12,30,1.62,64,65.27,1,1.46732,24.39,3,0.3107,44.99,42.53,9.36],
#           [9,31,1.6,67,63.91,0,1.44556,26.17,3,0.3216,43.36,40.97,9.01],
#           [14,32,1.6,48,48.72,0,1.20252,18.75,2,0.1855,39.68,36.91,8.12],
#           [17,32,1.67,51,52.9,1,1.2694,18.29,1,0.2036,42.12,38.75,8.53]]

if __name__ == '__main__':
    """input = [12,30,1.62,64,65.27,1,1.46732,24.39,3,0.3107,44.99,42.53,9.36]
    x, y = read_data.getdata()
    # 每日7类食物份数
    part_per_day = read_data.part_per_day([input], x, y)[2:]
    print(part_per_day)
    part = []
    # 重排序
    part_per_day[3] -= 1"""
    part_per_day = []
    part = []
    for i in range(1, len(sys.argv)):
        part_per_day.append((int(sys.argv[i])))
    part_per_day[3] -= 1
    for i in [0, 5, 3, 4, 1]:
        part.append(part_per_day[i])
    part.insert(1, 0)
    part.insert(3, 1)
    part[5] = 1
    # print('交换份数：',part)
    input = []
    food_units = [25, 25, 250, 60, 50, 100, 500]
    # 各类食物当日克数
    for i in range(7):
        input.append(float(part[i] * food_units[i]))
    # print(input)
    st=time.perf_counter()
    for i in range(1):
        # print("-------{}---------------------------------------------------".format(i))
        s0=time.time()
        main(input)
        # print("{:.3f} s".format(time.time()-s0))
    # print(time.perf_counter()-st)
