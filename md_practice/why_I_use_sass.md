Why I use Sass in a non sass project
===
by linden5

---

## 1.	Original purpose: avoid CSS conflict

My original purpose of using sass was actually to prevent CSS conflict in a single-page H5 app. The mentioned project didn't use any framework or technique to prevent css conflit, nor did it use sass. What makes things more complicated was that I found some colleagues had a habbit to modify others' code regardless of whether others' style were functioning properly. Since I had some limited experience of using sass, I came up with the following idea:

>**Wrap my page with a nonconflicting class and add this class selector before all my style definitions**

This would make my style conflict-free and prevent others from modifying my CSS file as well as providing flexibility to style reuse of others work.

Unfortunately, before I decided to do this, my CSS file had already grown a little to big. In order to make things easier, I chose to use sass privately.

>**No matter what the requirement of the project is, I would use sass.**

![an outer selector](/img/outer_selector.png)

## 2. Once started, do more

Once I started using sass, pure css became intolerable.

When using pure css, some frequently used style is usually bond to a single selector to make it resusable. Just as following:

	.hide { display: none; }
	.f_right { float: right; }
	.blue { background: #16b0f4}
	
I've seen a project that made this to its extreme:

![shocking html](/img/pay2_page_sample.png)

To tell you the truth, I was shocked the first time I saw it.

In more semantic practice, such definitions are also necessary.

Since I had began to use Sass, it was better to replace such definition with Sass variables:

	$stripHeight: 1.458333rem;
	$listItemHeight: 1.25rem;
	$fontSize: 0.4rem;
	$margin: 0.4rem;
	$gray80: #808080;
	$borderColor: #E6E6E6;
	$themeColor: #16b0f4;
	
So my SCSS file became to this:

![SASS var](/img/sample_var.png)

When it came to some selectors that share the same style,*(due to my lack of css structuring ability)*, for example, like this:

	.white-bar,
	.white-bar-heigher,
	.white-bar-nobottom-border {
        ...
    }
	
can be replaced by mixin:

![mixin](/img/sample_mixin.png)

used with inheritance, it look like this:

![inherit & mixin](/img/sample_inherit.png)

## 3. No more JavaScript

The was a requirement:

>**To show up to 4 record each time. If there are more, let it scroll-y**
>like the following：
>
>![how's it like](/img/sample_overflow.png)

To let it scroll, use `overflow-y:scroll`, to calculate its height, leave it to SASS:

![cal](/img/sample_calculate.png)

some selectors share nearly the same style but with different values? Use function instead:


![func](/img/sample_func.png)
![func](/img/sample_func2.png)

Two lines of text inherited line-height properties from parent，but should have a different margin? No need to refactor html, leave it to sass

![cal](/img/sample_line.png)

## 4. Once you have started using sass, you don't want to stop.

Here comes the question: why didn't the project leader switch to sass when it has already been widely used for several years?
---