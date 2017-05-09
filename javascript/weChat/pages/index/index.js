//index.js
//获取应用实例
var app = getApp();
var utils = require("../../utils/utils.js");

Page({
  data: {
    indicatorDots: true,
    autoplay: true,
    interval: 3000,
    duration: 500,
    sections: [
      "活期保",
      "定期保",
      "基金",
      "专家托管",
      "保险",
      "股票",
      "特权码",
      "敬请期待"
    ],
    hideModal: true,
    hideLoading: true,
    footerText: "随手记",
    recomendList: [
      {
        title: "新用户-限量抢购1元抵1万",
        type: "新用户专享",
        cate: 1,
        rate: "8.88%",
        rateType: "预期年化收益率",
        infoName: "期限",
        infoData: "8天",
        description: "新用户-限量抢购1元抵1万"
      },
      {
        title: "证券开户",
        type: "证券开户",
        cate: 0,
        rateType: "海通证券",
        infoData: "万2.5佣金、7*24贴心服务、龙头券商",
        description: "开户即送30元，入金再送30元，推荐又送30元"
      },
      {
        title: "长信量化中小",
        type: "基金",
        cate: 2,
        rate: "54.84%",
        rateType: "过去一年收益率",
        infoName: "最新净值(元)",
        infoData: "1.1020",
        description: "量化投资，刨除人性弱点，具有较高的投资效率"
      },
      {
        title: "国华天添益",
        type: "定期保",
        cate: 3,
        rate: "4.05%",
        rateType: "历史年化投资回报率",
        infoName: "期限",
        infoData: "灵活领取",
        description: "次日起息，灵活领取"
      }
    ]
  },
  onLoad: function() {
    var that = this;
    wx.request({
      url: "https://fcms.bestpay.com.cn/api/v1/bannerControl",
      data: JSON.stringify({
        oldBanID:"00000000",
        oldVersion:"1",
        channel:"01",
        account:"18925089018",
        type:"2"
      }),
      header: {
        "Authorization": "Basic " + JSON.stringify({
                              "systemType": "",
                              "systemVersion": "",
                              "appType": "01",
                              "appVersion": "",
                              "signType": "",
                              "sign": "",
                              "deviceName": "",
                              "imsi": "",
                              "imei": "",
                              "btMac": "",
                              "wifiMac": "",
                              "locInfo": "",
                              "productNo": app.globalData.userInfo,
                              "clientType": "01",
                              "sessionKey":app.globalData.sessionKey
                          })
      },
      method: "POST",
      success: function(res) {
        console.log(res);
        if (res.statusCode !== 200 || res.data.status !== "1") return;

        var bannerUrls = [];
        res.data.result.forEach(function(banner) {
          bannerUrls.push(banner.downloadUrl);
        });
        that.setData({
          bannerUrls: bannerUrls
        });
      }
    });
  },
  toSection: function(event) {
    if (event.currentTarget.dataset.index === "1") {
      utils.navTo(this, "../fixed/fixed");
    } else if (event.currentTarget.dataset.index === "0") {
      utils.navTo(this, "../demand/demand");
    } else {
      this.setData({hideModal: false});
    } 
  },
  modalConfirm: function() {
    this.setData({hideModal: true});
  },
  footerTap: require("../../templates/footer/footer.js").footerTap
})
