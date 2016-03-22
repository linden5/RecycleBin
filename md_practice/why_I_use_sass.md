Why I use Sass
===
by linden5

---

## 1.	最初的目的: 避免CSS冲突

单页面应用，然而其前端框架中并没有任何防止样式冲突的机制。因此在工作的过程中，如果两个人定义的样式起了冲突，很可能会出现其中一个人不明不白地将另一个人的样式删除的情况。为了避免这种麻烦，我想到了之前的做法：

>**将我写的页面用一个div包围起来，取一个不与任何人的页面冲突的class名，所有css选择器前面多加一个class选择器。**

这样一来，我的样式就不会和别人冲突了，基本杜绝了我的样式被别人改掉的可能性，而且因为选择器加了一层，我也可以更灵活地使用同事写的样式。

然而非常不幸的是，在想到要这么做之前，我的CSS文件已经写了很长了，因此我决定直接用SASS，这样就只用在最外层嵌套一层选择器就可以了。

>**不管别人用不用、项目用不用，反正我是用了**

![最外层嵌套一个class选择器](/img/outer_selector.png)

## 2. 一不做，二不休

既然已经上了SASS这条船了，之前的纯CSS代码就变得令人无法忍受了。

一般来说，使用纯CSS的时候，会对一些常用的样式定义一个选择器以方便样式复用，比如下面这样：

	.hide { display: none; }
	.f_right { float: right; }
	.blue { background: #16b0f4}
	
这样的写法很方便样式的复用，比如2期，就把这种方式用到了极致：

![交费助手2期html代码](/img/pay2_page_sample.png)

说实话，第一次看到这样的html代码，我被吓到了，也被吓到了。

3期的页面放弃了这种做法，改用了更容易被大家接受的语义化的class命名方法，CSS选择器也没有这么碎片化了，然而对于字体颜色之类的，页面边距之类的，还是不免要单独定义一个class以便于样式复用。

既然已经使用了SASS，显然放弃原来纯CSS的方法，用SASS的方法来实现样式的复用更好，于是我把自己的CSS文件中类似上面的针对单条样式声明定义一个选择器的做法，改成了使用SASS变量代替：

	$stripHeight: 1.458333rem;
	$listItemHeight: 1.25rem;
	$fontSize: 0.4rem;
	$margin: 0.4rem;
	$gray80: #808080;
	$borderColor: #E6E6E6;
	$themeColor: #16b0f4;
	
于是样式就写成了这样：

![SASS变量](/img/sample_var.png)

对于多个选择器共用同样的样式声明*(这种情况的出现可能与我个人CSS结构掌控能力不足有关)*，比如下面这样的:

	.white-bar,
	.white-bar-heigher,
	.white-bar-nobottom-border
	
就可以使用mixin来代替:

![mixin](/img/sample_mixin.png)

结合继承一起使用，看起来就是这样的

![继承与mixin](/img/sample_inherit.png)

## 3. 不想写JavaScript

模块里面有这样的一个需求：

>**显示记录，如果记录超过4个，只显示4个，其余的在显示区域滑动显示**
>效果图如下：
>
>![效果图](/img/sample_overflow.png)

其实也就是一个overflow-y:scroll的问题，问题是，需要给这个区域定个高度，高度是多少要自己算。所以，交给SASS吧；

![计算](/img/sample_calculate.png)

有两个样式其实样试声明一样，只是值不同怎么办？写个函数吧


![函数](/img/sample_func.png)
![函数](/img/sample_func2.png)

两行文字都继承了父元素的line-height，但这两行的间距又有特别的要求？懒得改html结构了，让SASS帮你算吧

![函数](/img/sample_line.png)

## 4. 一但用过一次SASS，就根本停不下来！
