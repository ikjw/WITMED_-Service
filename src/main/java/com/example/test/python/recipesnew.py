#encoding=utf-8
import pymysql as ps
import pymysql.cursors
import random
import time
def get_recipe(num_recipe=1,main_food=None,extra_food=None,labels=None,not_in_mainfood=None,recipes_type=None):
    # print(main_food,extra_food,labels,not_in_mainfood,recipes_type)
    # conn = ps.connect(host='127.0.0.1',port=3306,user='admin',passwd='123456',database='recipe_old',charset ='utf8')
    conn = ps.connect(host='localhost',port=3306,user='root',passwd='123456',database='food',charset ='utf8')
    cursor = conn.cursor()
    sql = '''SELECT id FROM recipesall WHERE '''
    if recipes_type is None and main_food is None and extra_food is None and labels is None and not_in_mainfood is None:
        return None
    if recipes_type is not None and len(recipes_type) != 0:
        for each_type in recipes_type:
            sql = sql + '''  instr(recipesall.菜谱类别,"%s") > 0  ''' %(each_type) + "AND"

    if main_food is not None and  len(main_food) != 0:
        for each_mainfood in main_food:
            sql = sql + '''  instr(recipesall.主料,"%s") > 0  ''' %(each_mainfood) + "AND"

    if extra_food is not None and len(extra_food) != 0:
        for each_extra_food in extra_food:
            sql = sql + '''  instr(recipesall.辅料,"%s") > 0  ''' %(each_extra_food) + "AND"

    if labels is not None and len(labels) != 0:
        label_keys = list(labels.keys())
        label_values = list(labels.values())
        for i in range(0,len(label_keys)):
            sql = sql + '''  instr(recipesall.%s,"%s") > 0  '''%(label_keys[i],label_values[i]) + "AND"
    if not_in_mainfood is not None and len(not_in_mainfood) != 0:
        for each_not_in_mainfood in not_in_mainfood:
            sql = sql + '''  instr(recipesall.主料,"%s") = 0 ''' %(each_not_in_mainfood) + "AND"
    sql = sql[:-3]
    sql = sql + ";"
    cursor.execute(sql)
    select_r = cursor.fetchall()
    if len(select_r) == 0:
        return None     
    recipes_id = []

    for item in select_r:
        for each in item:
            recipes_id.append(str(each))
    recipes_result = []
    if len(recipes_id) < num_recipe:
        for i in range(0,len(recipes_id)):
                id = recipes_id[i]
                sql = "SELECT content FROM recipescontent WHERE id = %s;"   
                cursor.execute(sql,[id])
                select_result =cursor.fetchone()
                for item in select_result:
                    item = eval(item)
                    recipes_result.append(item)
    else:
        i = 0
        while i < num_recipe:
            key = random.randint(0,len(recipes_id)-1)
            id = recipes_id[key]
            sql = "SELECT content FROM recipescontent WHERE id = %s;"   
            cursor.execute(sql,[id])
            select_result =cursor.fetchone()
            for item in select_result:
                item = eval(item)
                recipes_result.append(item)
            i += 1
    conn.commit()
    cursor.close()
    conn.close()
    return recipes_result
        
# rlabels = {'菜系':'东北菜','加工工艺':'炖','口味':'鲜香'}
# rextrafood = ['油','盐']
# # rmainfood = ['高筋面粉','鸡蛋']
# rmainfood = ['肝']
# rtype = ['蔬菜']
# aa = get_recipe(num_recipe=10,main_food=rmainfood)
# for item in aa:
#     print(item)

            