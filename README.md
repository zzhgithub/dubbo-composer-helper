# Dubbo Composer Helper

生成 dubbo composer 风格api的助手。

## 生成规则

1. 类认为是Dto 进行处理 如果遇到嵌套对象则 直接生成require
2. 对特定的泛型进行处理（如,List）
3. 接口认定为接口。生成对应的const 参数名择对泛型进行特殊处理 如果是复杂类型，或者是dto直接使用
requie进行引入

## 路径规则
一律lib下。路径和java中包相同
index中引入所有的Service

## package.json

## readme.md 生成

## git忽略文件生成

cicd 支持 还有nodejs(主要暴露cli和美化代码)支持!