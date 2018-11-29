# Java Collection --- Set



- HashSet
- TreeSet

## HashSet

HashSet用于快速查找所需对象, 它为每个对象(Object::hashCode)计算一个HashCode.

HashSet基于LInkedList实现, 每个列表称为桶(bucket). 

对象保存的位置: **HashCode % count(bucket) = BucketIndex**.

散列冲突

桶数

再散列



## TreeSet



