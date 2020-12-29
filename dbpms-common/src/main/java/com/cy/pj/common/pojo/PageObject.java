package com.cy.pj.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**借助此对象在业务逻辑层封装分页信息(可以理解为业务层pojo对象)
 * 记住:在java所有封装数据对象,尽量都是实现Serializable接口,目的是方便对对象进行序列化和反序列化
 * 1)序列化:将对象装换为字节
 * 2)反序列化:将字节转换为对象
 * 序列化的应用场景?
 * 1)将对象转换为字节以后缓存到内存
 * 2)将内存中的对象存储到文件
 * 3)将对象转换为字节在网络中进行加密传输
 * 序列化对象设计规范?
 * 1)实现序列化接口Serializable
 * 2)添加序列化ID
 * .................................
 * */
public class PageObject<T> implements Serializable {//当泛型定义在类或接口名的右侧时这种泛型叫类泛型
     private static final long serialVersionUID = -8770600233998974797L;
     /**总记录数*/
     private Integer rowCount;
     /**当前页记录*/
     private List<T> records;//new PageObject<SysUser>(),new PageObject<SysRole>()
     /**总页数*/
     private Integer pageCount;
     /**每页最多显示多少条记录**/
     private Integer pageSize;
     /**记录当前页的页码值*/
     private Integer pageCurrent;

     public PageObject(Integer rowCount, List<T> records, Integer pageSize, Integer pageCurrent) {
          this.rowCount = rowCount;
          this.records = records;
          this.pageSize = pageSize;
          this.pageCurrent = pageCurrent;
          this.pageCount=rowCount/pageSize;
          if(rowCount%pageSize!=0)this.pageCount++;
     }
}
