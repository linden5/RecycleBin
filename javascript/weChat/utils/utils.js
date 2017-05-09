module.exports = {
    navTo: function(page, url) {
        page.setData({hideLoading: false});

        setTimeout(function() {
        wx.navigateTo({url: url});
        page.setData({hideLoading: true});
        }, 300);
    }
}