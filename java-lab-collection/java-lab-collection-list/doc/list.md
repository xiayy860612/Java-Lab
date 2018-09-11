# List

List用于描述有序集合, 即元素按固定的顺序排列.

- Array数组
- ArrayList数组列表
- LinkedList链表

## LinkedList

链表是一个双向列表.

迭代器描述的是集合中的位置, 当使用迭代器添加元素时, 只有对**有序集合**有意义.

LinkedList只能通过迭代器来对元素进行访问.

Collection可以跟踪改写操作的次数, 每个迭代其都维护了一个独立的计数器, 
在调用迭代器的方法时, 会检查自己改写操作的计数和Collection改写的计数是否一致.
如果不一致, 则抛出ConcurrentModificationException.

ListIterator::set方法不被认为是对结构的修改操作.

**链表不支持快速的随机访问**, 所以当程序需要采用整数索引来访问元素时, 通常不使用链表.
所以不应该使用LinkedList::get方法.

使用链表的理由是尽可能地减少在列表中插入/删除元素所付出的代价.
