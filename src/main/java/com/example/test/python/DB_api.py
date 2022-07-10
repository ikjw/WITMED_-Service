# 根据菜谱类型，查询菜谱
# 根据一个或者多个食材，查询菜谱
# 根据各种标签（标签，加工工艺，口味，功效，人群，疾病，体质，菜系，小吃，菜品，场景），查询菜谱

def get_DBrecipe(num_recipe=1, recipe_type=None, main_food=None, not_in_main_food=None, extra_food=None, label=None):
    # recipe_tyep : list, 包含一个或多个字符串，如 [ '肉类' ，'蔬菜类' ]
    # main_food : list，主料，形式同上
    # not_in_main_food : list，不包含在主料，形式同上。检索主料里不包含这些食材的菜谱
    # extra_food : list，辅料，形式同上
    # label : list，标签，形式同上。包括（标签，加工工艺，口味，功效，人群，疾病，体质，菜系，小吃，菜品，场景）
    # num_recipe : int 返回菜谱的数量
    #
    # return ：
    #           recipes : list，包含多个菜谱，每个菜谱返回其json文件转换得到的dict
    pass