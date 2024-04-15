# 更适合中国宝宝体质的CS61B自学指南

我是在跟着21年春季的课程网站学习CS61B，这里是课程的[官方网站](https://sp21.datastructur.es/index.html)，
学习过程中发现一些问题，因此记录下来，希望能够帮助后来者。

## proj0
issue: 如果你严格按照课程的要求去做proj0，你会发现当你测试`tilt()`函数时运行GUI窗口，你的方向键输入是无效的，
这是因为中文系统的默认语言是中文，如果你在`Game.java`中`playGame()`方法下的`String cmnd = _source.getKey()`这行命令下加上一个`System.out.println(cmnd)`，
你会发现你输入方向键打出来的内容实际上是"向上箭头"、"向下箭头"、"向左箭头"、"向右箭头"，也就是说在接下来的`switch(cmnd){}`中的`tilt()`函数根本就执行不到！

solution: 打开`GUISource.java`，找到`getKey()`方法，将它开头几行的case后面字符串修改成中文如下：
```java
    public String getKey() {
        String command = _source.readKey();
        switch (command) {
            case "向上箭头" :
                command = "Up";
                break;
            case "向右箭头" :
                command = "Right";
                break;
            case "向下箭头" :
                command = "Down";
                break;
            case "向左箭头" :
                command = "Left";
                break;
            default :
                break;
        }
```
