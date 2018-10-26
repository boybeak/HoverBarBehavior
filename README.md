## HoverBarLayout

![demo](https://github.com/boybeak/HoverBarBehavior/blob/master/1.gif)



## FloatingActionMenu

![demo](https://github.com/boybeak/HoverBarBehavior/blob/master/2.gif)



```kotlin
FloatingActionMenu(this)
	.setDimView(content)
	.setAnchorView(fab)
	.inflate(R.menu.menu_demo)
	.setOnCreatedListener { designMenu, famItemViews ->
	}
	.setOnItemSelectedListener {item ->
		when(item.itemId) {
		}
		true
	}.show()
```

