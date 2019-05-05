package com.itcast.dto;

public class EEnum {
    public enum DBtype{
        mysql("mysql",0),oracle("oracle",1),sqlserver("sqlserver",2),sqllite("sqllite",3);
        public Integer value;
        public String name;
        DBtype(String name,Integer value){
            this.name=name;
            this.value=value;
        }

        public static DBtype getModel(Integer value){
            for(DBtype item: DBtype.values()){
                if(item.value.equals(value)){
                    return item;
                }
            }
            return null;
        }
    }
    
    public enum MqAction{
        /**
         * 处理成功
         */
        ACCEPT,
        /**
         *  可以重试的错误
         */
        RETRY,
        /**
         * 无需重试的错误
         */
        REJECT,
        /**
         * 放入死信
         */
        DEATH,
    }

    public enum ActivityEventType{
        laxin("laxin","1"),yaoqing("yaoqing","2"),xiaofei("xiaofei","3"),huiyuanpaiquan("huiyuanpaiquan","4"),
        zhijiang("zhijiang","5"),shoudanlijian("shoudanlijian","6");
        public String name;
        public String value;
        ActivityEventType(String name,String value){
            this.name=name;
            this.value=value;
        }
        public static ActivityEventType getModel(String value){
            for (ActivityEventType item: ActivityEventType.values()){
                if(item.value.equals(value)){
                    return item;
                }
            }
            return null;
        }
    }

    /**
     * 交易类型
     * 支付接入交易类型说明：
     * 接入支付交易类型段，目前开通40至49,51至59,60至69
     * 建交易类型的时候，不要跳号，顺着往下加。有空缺的，可以使用空缺。
     * 建了枚举，就提交一下，避免大家冲突。
     * 其它说明：
     * 充值卡是为了查询时候生成积分商城的流水号的。定义了充值卡的类型。
     */
    public enum BsnsType {
        Product("商品兑换", 10), Draw("积分抽奖", 11), Reward("积分赠送", 20),RewardEPC("充值卡赠送", 21),
        Rechargeable("充值卡",30),SBX("集采购物",80),Bill("商城购物",88),CardCoreRefund("退款单",89),Refund("退款单",90),Invoice("发票",50),
        Refueling("加油支付",40),APPRefueling("APP加油支付",41),Vrcard("虚拟卡",42),Fzs("蜂助手充值",46),SinochemFilm("中化电影票",47),SinochemCZK("中化储值卡",48), SinochemFilm2("中化电影票2期",49),
        SinochemChildact("中化非油活动",51),SinochemOneKeyOil("中化一键加油",52),AppCardApply("贵州在线办卡",53),APPCOUPONBUY("电子券售卖",54),WXXCOUPONBUY("微信小程序售卖电子券",55), SinochemOrderConcert("中化演唱会票",56),BatchOil("河北商客购油",57),
        NetRecharge("网厅充值",60),AppPayCommon("贵州电费充值",61),WXGCOUPONBUY("微信公众号售卖电子券",62),SinochemOneKeyOilNoPBH("中化一键加油",58),UnifiedPay("智能洗车",18);//对外支付接口;
        public int value;
        public String text;
        private BsnsType(String text, int value) {
            this.value = value;
            this.text = text;
        }
        public static String getText(int value){
            String restext="";
            for(BsnsType bsnsType: BsnsType.values()){
                if(bsnsType.value==value){
                    restext = bsnsType.text;
                    break;
                }
            }
            return  restext;
        }
    }

    public enum RespCodes {
        Success("1001","操作成功"),
        Fail("1002","操作失败"),
        SignWrong("1003","签名错误"),
        SignRepeat("1004","签名重复"),
        JsonDataWrong("1005","业务参数错误"),
        SysDataWrong("1006","系统参数错误"),

        UnKnowMarking("1008","无法识别的系统标识"),
        UnKnowBizid("1009","无法识别的路径标识"),
        IllegalMobile("1010","非法手机号"),
        PicValidCodeError("1011","图片校验码输入错误"),
        SMSSendError("1012","短信验证码异常"),
        SMSCodeExist("1013","输入验证码不能为空"),
        SMSCodeExpire("1014","验证码已过期失效，请重新获取"),
        SMSCodeError("1015","短信校验码输入错误"),
        ReferrerError("1016","推荐码错误"),
        ReferrerInfoError("1017","未找到该推荐码信息"),
        PwdFormatError("1018","密码格式错误"),
        UserPwdError("1019","密码错误"),
        UserOldPwdError("1020","原密码错误"),
        UserNeedCheckOldPwd("1021","请您先验证原密码!"),
        UserNeedCheckOldPhone("1022","请您先验证原手机号!"),
        UserNeedCheckPhone("1023","重置密码前，请先校验注册手机号!"),
        UserNameError("1024","用户名格式错误，请输入2至15位字符串。"),
        UserNameExist("1025","此用户名已被使用！"),
        UserHasBoundPhone("1026","您已经绑定了手机号，请直接使用手机号登录。"),
        PreUserStationHasJoin("1027","此账号对应油站已经入驻，绑定的手机号为mobile，请直接登录使用，如有疑问请联系客服400-619-1866"),

        UserLevelSetError("1100","会员等级设置异常"),
        UserLoginError("1101","会员登录异常"),
        UserPwdModifyError("1102","修改密码异常"),
        NeedResetPwd("1103","需要重置密码"),
        TokenError("1104","Token异常"),
        UserNotExist("1105","该手机号未注册！"),
        UserNotRegisterPhone("1106","输入手机号不是您注册时的手机号"),
        PhoneRegister1("1107","该手机号已经注册过,不能重复注册！"),
        PhoneRegister2("1108","该手机号已经注册过，不能修改为新手机号！"),
        ResetPwdNoRegister("1109","该手机号暂未注册，请先注册。"),//重置（忘记）密码时，先判断是否注册过
        RegisterTest("1110","目前处于内测阶段，敬请期待！"),
        RegisterPause("1111","暂停注册，请您稍后再试！"),
        LoginLimit("1112","该设备登录数量已达上限，无法登录。"),
        RegisterLimit("1113","该设备注册数量已达上限，无法注册。"),
        EmployeeNotRegister("1114","该员工尚未注册会员"),
        ReferCodeIllegal("1115","该推荐码不合法"),
        ThirdLoginFailToBind("1116","第三方授权登录失败，请去绑定第三方账号"),
        UserIdNotExist("1117","该会员编码不存在"),
        EmpNoNotExist("1118","该员工编码不存在"),
        PhoneNotBound("1119","该手机号未绑定"),
        PhoneIsBinding("1120","该手机号已经绑定"),
        PhoneIsEmpty("1121","手机号为空"),
        BindingFailure("1122","绑定失败"),
        BindingSuccess("1123","绑定成功"),




        CardNotExist("1200","尊敬的用户，您的加油卡号不存在"),
        CardHasBound("1201","尊敬的用户，该加油卡已经被绑定了！"),
        CardReservedPhoneNotConsistent ("1202","尊敬的用户，您输入的手机号与开卡预留手机号不一致，预留手机号："),
        CardNotExistOrReserverPhone ("1203","尊敬的用户，您的加油卡不存在或者没有预留手机号"),
        CardAssistant ("1204","尊敬的用户，您的加油卡为副卡，本应用不能绑定副卡"),
        CardNotReserverPhone ("1205","尊敬的用户，您的加油卡没有预留手机号"),
        CardCanNotBound ("1206","尊敬的用户，您的加油卡暂不支持绑定业务！"),

        GasStationNotOpen ("1301","尊敬的用户，本加油站尚未开通APP支付功能！"),
        GasOilOrderError("1302","订单状态异常"),
        GasOilNoOrder("1303","订单不存在"),//app加油支付订单不存在
        GasOilPaying("1304","正在支付中"),
        GasOilPaySuccess ("1305","支付成功"),//1001-表示当有第三方支付时，支付成功，需要等待回调确认成功；1305-表示当只有虚拟支付时，支付成功，无回调
        GasOilPayFail ("1306","支付失败"),

        ServerKeyInvalid("3000","服务端公钥过期失效"),
        ClientPublicKeyIllegal("3001","客户端公钥非法"),
        StartAdClosed("4000","广告已关闭"),
        FirstLoginCoupon("6001","首次登录派发电子券"),
        CouponEmpty("6003","电子券库存不足"),

        WalletPwdRight("7001","钱包支付密码匹配"),
        WalletPwdWrong("7002","钱包支付密码不匹配"),
        WalletHandPwdRight("7003","钱包手势密码匹配"),
        WalletHandPwdWrong("7004","钱包手势密码不匹配"),
        PayPwdWrong("7005","支付密码不匹配"),
        PayPwdEmpty("7006","支付密码不能为空"),
        OriginalPayPwdWrong("7007","原支付密码错误"),
        PayPwdOpened("7008","支付密码已开通，无需再次开通"),
        PayPwdClosed("7009","支付密码未开通，请先开通支付密码"),
        PayPwdPhoneWrong("7010","开通支付密码，手机号不匹配"),

        PicValidcodeRefresh("8000", "图片验证码需要刷新"),
        PointRecordISNotExist("2000","积分记录不存在"),

        UnKnowError("9000","未知异常");

        private String value;
        private String text;

        RespCodes(String value, String text){
            this.value = value;
            this.text = text;
        }

        public String getValue(){
            return this.value;
        }
        public String getText(){
            return this.text;
        }
    }

    public enum CardTypeCodes {
        //礼品卡：0；  会员卡：3；   储值卡：4；  支付宝：7；
        // 微信：8；  货车帮：11；   银行卡：12    其它：99
        GiftCard("0","礼品卡"),
        MembershipCard("3","会员卡"),
        ValueCard("4","储值卡"),
        Alipay("7","支付宝"),
        WeChat("8","微信"),
        TruckToHelp("11","货车帮"),
        BankCard("12","银行卡"),
        Other("99","其它");

        private String value;
        private String text;

        CardTypeCodes(String value, String text){
            this.value = value;
            this.text = text;
        }

        public String getValue(){
            return this.value;
        }
        public String getText(){
            return this.text;
        }
    }

    public enum PayModeCodes {
        //支付方式；-1:未支付(为防止班结信息失败加入的初始值  不用做判断是否支付成功) 0：微信支付；
        // 1：支付宝支付;2:电子券支付；3:积分卡；= 会员卡      4：储值卡,5:银行卡,6:现金',

        Unpaid("-1","未支付"),
        WeChat("0","微信支付"),
        Alipay("1","支付宝支付"),
        ElectronicCoupons("2","电子券支付"),
        MembershipCard("3","积分卡"),
        ValueCard("4","储值卡"),
        BankCard("5","银行卡"),
        Cash("6","现金");

        private String value;
        private String text;

        PayModeCodes(String value, String text){
            this.value = value;
            this.text = text;
        }

        public String getValue(){
            return this.value;
        }
        public String getText(){
            return this.text;
        }
    }

    public enum PointsType {

        Refund("撤销消费", "07"),
        POS("线下消费", "14"),
        chongzheng("消费冲正","08"),
        chongzhengCancle("消费撤销冲正","17");


        public String text;
        public String value;

        PointsType(String text, String value) {
            this.text = text;
            this.value = value;
        }

        public static PointsType getPointsType(String value) {
            for (PointsType os : PointsType.values()) {
                if (os.equals(value)) {
                    return os;
                }
            }
            return null;
        }
    }

    /**
     * 积分消费来源
     */
    public enum PointsFromName {

        POS("pos",1),
        N900("N900",2),
        EDC("edc",3);


        public String text;
        public Integer value;

        PointsFromName(String text,Integer value){
            this.text=text;
            this.value=value;
        }

        public static PointsFromName getPointsFromName(Integer value){
            for (PointsFromName os : PointsFromName.values()) {
                if (os.equals(value)) {
                    return os;
                }
            }
            return null;
        }
    }


}
