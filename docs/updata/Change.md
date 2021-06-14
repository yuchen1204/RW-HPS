# RW-HPS

## 2021-5-2
1. 我们将原先的依赖关系如图  
   ![][image1]  
   现在已切换为  
   ![][image2]  
2. Plugin的实例现在已进行池化,将在Core内便于管理  
    - 为后面的动态加载做铺垫  
3. 数据的读取API进行修改,原来的getString()/getObject()统统切换为getData()  
4. 协议上的更改
    - 支持了伪重连  
    - 修复UDP的不稳定

[image1]:data:image/png;base64,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
[image2]:data:image/png;base64,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