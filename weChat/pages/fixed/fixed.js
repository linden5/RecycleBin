Page({
    data: {
        selfSelect: true,
        fossick: false,
        selfList: [
            {
                name: "限量抢购1元抵1万",
                percent: 70,
                period: 35,
                rate: 8.88,
                startAmount: 1000,
                startDate: "2016.10.01",
                type: "newuser"
            },
            {
                name: "钱橙甜穗宝182天68期026",
                percent: 10,
                period: 182,
                rate: 4.70,
                startAmount: 1000,
                startDate: "2016.10.01",
                type: "pre",
                dateTime: "10-27 9:00"
            },
            {
                name: "国华天添益",
                percent: 90,
                rate: 4.05,
                startAmount: 100,
                startDate: "2016.10.01"
            },
            {
                name: "钱橙甜穗宝365天60期021",
                percent: 46,
                period: 365,
                rate: 5.79,
                startAmount: 1000,
                startDate: "2016.10.01",
                type: "canTrans"
            },
            {
                name: "钱橙甜穗宝91天60期021",
                percent: 100,
                period: 91,
                rate: 3.80,
                startAmount: 1000,
                startDate: "2016.10.01",
                type: "canTrans"
            }
        ],
        fossickList: [
            {
                name: "定期保-钱橙甜穗宝35天47期045",
                percent: 40,
                period: 35,
                rate: 3.80,
                isTrans: true,
                startAmount: 100256.90,
                startDate: "2016.10.14"
            },
            {
                name: "定期保-钱橙甜穗宝182天49期045",
                percent: 100,
                period: 182,
                rate: 4.16,
                isTrans: true,
                startAmount: 200256.90,
                startDate: "2016.10.14"
            },
            {
                name: "定期保-钱橙甜穗宝365天47期015",
                percent: 100,
                period: 365,
                rate: 5.80,
                isTrans: true,
                startAmount: 400256.90,
                startDate: "2016.10.14"
            }
        ],
        footerText: "随手记",
    },
    chooseSelf: function() {
        this.setData({
            selfSelect: true,
            fossick: false,
        });
    },
    chooseFossick: function() {
        this.setData({
            selfSelect: false,
            fossick: true,
        });        
    },
    footerTap: require("../../templates/footer/footer.js").footerTap
});