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

## proj1
issue: Hug在课上讲了一种在`public boolean equal(Object o)`这个从Object重载的method中判断对象类别的java特性，用类似于`if(o instanceof Deque deque){}`这样的语句同时完成类别判断和赋值两件事，但是autograder貌似不支持这一操作...

solution: 老老实实的把这行命令拆成了两行:
```java
if (o instanceof Deque) {
    Deque deque = (Deque) o;
    ...
}
```

## lab4a
issue: 由于课程已经完成了，所有项目文件都是直接克隆下来的，助教当时设置的合并冲突无法通过pull出现了，只能想办法回溯到过去的状态。

solution: 参考[这位老哥的cs61b笔记](https://github.com/lyorz/CS61B-labs)lab4部分，新建一个分支onlyforlab4回到之前的状态。
```bash
git checkout dd477f37b7c97f22868aa7482e2391fbced60bf6
git branch onlyforlab4
git checkout onlyforlab4
```
回到了lab1还没做的时候，这个时候重做一遍lab1并提交，然后就可以`git pull skeleton master`了，最后用`git push --set-upstream origin onlyforlab4`把onlyforlab4分支传到github的仓库中。

## lab6
issue: 在完成最后一部分"Mandatory Epilogue: Debugging"时，在命令行中运行
`python runner.py --debug our/test02-two-part-story.in`报错，提示`KeyError: 'REPO_DIR'`。

solution: 由于cs61b用的批处理软件是git bush，我们只需要输入下面一行命令
```bash
export REPO_DIR=[这里输入你的cs61b仓库的路径]
```
将这个环境变量改成正确的即可，之后运行的时候可能能成功进去，但是还是提示路径不对，这时要检查一下仓库名字，运行runner.py需要git仓库必须要命名成"sp21-s*"，把文件夹重命名一下就可以了。

## proj2
这个项目我把助教说的最难debug的错误给撞上了，我用HashMap实现了Commit中文件名到物体的映射列表，结果序列化的时候怎么也不对，调试的时候成了名副其实的"Heisen Bug"，有一半的时间是错的，另一半时间跑的是对的，
我用了半天的时间，才发现这个东西序列化不能用HashMap，因为HashMap中键值对的顺序是随机的，而这个会影响计算哈希值的结果。。。做完merge之后就没有动力再继续做下去了，感觉merge的代码我写的和坨屎差不多，
很多辅助函数和域都是为了图省事加上去的，"完全没有初始的构思和架构"，程序最后在GradeScope上跑了1300多分，还有一点小bug，远程的Command没有实现，但是我觉得这个项目我确实把所有需要学的东西都学到了，
再继续做下去费时费力，而且性价比不高，因此就做到这里罢！
