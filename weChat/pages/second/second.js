//second.js
Page({
  data: {
    monthIncome: 1111,
    monthExpense: 300,
    balance: 666,

    totalIncome: 10000,
    totalExpense: 5000,

    details: [
      { cate: 0,
        date: "2016.10.01",
        type: "吃饭",
        amount: 200 },
      { cate: 1,
        date: "2016.09.21",
        type: "工资",
        amount: 10000 },
      { cate: 0,
        date: "2016.10.01",
        type: "还信用卡",
        amount: 5000 },
    ],
    monthBalance: [
      {
        period: "9月",
        income: "10000",
        expenses: "10000"
      },
      {
        period: "8月",
        income: "10000",
        expenses: "10000"
      }
    ],
    yearBalance: [
      {
        period: "2016年",
        income: "10000",
        expenses: "10000"
      },
      {
        period: "2015年",
        income: "10000",
        expenses: "10000"
      }
    ],
    doAdd: false
  },
  showAdd: function() {
    this.setData({doAdd: true});
  },
  closeAdd: function() {
    this.setData({doAdd: false});
  }
})
