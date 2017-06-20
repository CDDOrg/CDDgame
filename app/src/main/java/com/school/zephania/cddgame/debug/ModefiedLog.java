package com.school.zephania.cddgame.debug;

/**
 * Created by user0308 on 5/30/17.
 */

public class ModefiedLog {
    /**
    17-05-30
    by user0308
    1:修改所有 ccd 为 cdd
    2:增加 bean, controller, debug, model.localdatabase package
    3:增加 PlayerData, Debug, ModefiedLog, LocalDatabaseController, LocalDatabaseHelper, PlayerDataModel 类
    4:把 CDDgame 移入 controller package
    */

    /**
     *
     *@author Yuan Qiang
     *created at 2017/5/30 14:34
     *  5、由于各自代码差异过大，因此重建项目
     *  6、基本整合三人代码
     */
    
    /**
     *
     *@author Yuan Qiang
     *created at 2017/6/7 20:49
     *更改排序函数，将getNumber()改为getNum()，修正排序花色顺序不对的bug
     * 增加一个类TypeNumCouple
     * 在Player中增加私有变量sendCardTag,以及相应的getter和setter
     * 在Player中增加西游变量couple
     * 修改并完善Player中的出牌函数sendCard()
     * 在Player中的onTouch()方法中增加了修改能否出牌的逻辑，并且多增加一个TypeNumCopuple的参数
     * 在God中部分实现dealWithChupai()和dealWithPass()函数
     * 在God中增加turn()函数
     * 未测试
     */

    /** @author Zephania
     * created at 2017/6/8 23.33
     * 修复无法按键的bug
     * 修复最后一张牌按键区域的bug
     * 增加一种出牌按钮的状态，即不出牌为灰色。
     * Player增加一个数组sendcard记录出牌的牌，增加一个sendstate记录出牌状态，调整了出牌函数，增加了threadcontrol，以实现停顿效果，
     * Player增加一个函数paintchupai，调整paint函数，增加出牌动画
     *
     */

    /**
     *
     *@author Yuan Qiang
     *created at 2017/6/10 21:21
     * 修复选三带二程序崩溃的bug
     * 修复出牌按钮一旦变亮再不变暗的bug
     */

    /**
     *  @author user0308
     *  created at 2017-6-20 19:51
     * 在 model 中增加了 AIDataModel类,只涉及 AI 算法方面,没有 view 相关
     * 在 model 中为 Card 类增加 CompareTo 方法, printInfo 方法,printInfo方法在控制台打印信息,可能到时会改为Log.d
     * 在 model 中新增 Couple 类,包含两个成员: counts (int) 和 number (ArrayList),代表有 counts 张同样大小的牌,这些牌存储在 number 中
     * 其实 counts 的值就是 number.size() 的值,由于使用时可能会出现 类A.类B.成员.函数() 这种复杂的结构,故而拆解,可能到时会重构一下这个类,命名太不规范
     * 在 debug 包中增加了 testAIMain 类作为测试 AI 的主类
     */
}
