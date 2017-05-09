//app.js
App({
  onLaunch: function () {
    var that = this;
    //调用API从本地缓存中获取数据
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
          //调用登录接口
      wx.login({
        success: function () {
          wx.getUserInfo({
            success: function (res) {
              console.log(res);
              typeof cb == "function" && cb(that.globalData.userInfo)
            }
          })
        }
      })
  },
  globalData:{
    userInfo:"18925089018",
    sessionKey: ""
  }
})