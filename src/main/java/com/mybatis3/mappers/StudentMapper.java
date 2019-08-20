package com.mybatis3.mappers;

import java.util.List;
import java.util.Map;

import com.mybatis3.domain.Address;
import org.apache.ibatis.annotations.*;

import com.mybatis3.domain.Student;



/**
 * @author Siva
 *
 */
public interface StudentMapper
{

	@Select("select * from students")


	@Results({
			@Result(id=true, column="stud_id", property="studId"),
			@Result(column="name", property="name"),
			@Result(column="email", property="email"),
			@Result(column="addr_id", property="address.addrId")
	})
	/**
	 * @Results 注解表示sql查询返回的结果集，@Results是以@Result为元素的数组，
	 * @Result表示单条属性-字段的映射关系，如：
	 * @Result(id = true, property = "id", column = "test_id", javaType = String.class, jdbcType = JdbcType.VARCHAR)
	 * 可以简写为：@Result(id = true, property = "id", column = "test_id")，
	 * id = true表示这个test_id字段是个PK，
	 * 查询时mybatis会给予必要的优化，应该说数组中所有的@Result组成了单个记录的映射关系，而 @Results则单个记录的集合
	 */
	List<Student> findAllStudents();

	@Select("select stud_id as studId, name, email, addr_id as 'address.addrId', phone from students")
	List<Map<String,Object>> findAllStudentsMap();

	//通过.操作符 给自定义字段赋值
	@Select("select stud_id as studId, name, email, addr_id as 'address.addrId', phone from students where stud_id=#{id}")
	Student findStudentById(Integer id);

	/**
	 *注解根据返回类型，推断如何组装数据
	 */
	@Select("select stud_id as studId, name, email, addr_id as 'address.addrId', phone from students where stud_id=#{id}")
	Map<String,Object> findStudentMapById(Integer id);

	/**配合xml 可以查询自定义字段详情*/
//	@Select("select stud_id, name, email, a.addr_id, street, city, state, zip, country"+
//  		" FROM students s left outer join addresses a on s.addr_id=a.addr_id"+
//		" where stud_id=#{studId} ")
//	@ResultMap("com.mybatis3.mappers.StudentMapper.StudentWithAddressResult")
//	Student selectStudentWithAddress(int studId);



	 @Select("SELECT ADDR_ID AS ADDRID, STREET, CITY, STATE, ZIP, COUNTRY FROM ADDRESSES WHERE ADDR_ID=#{id}")
	 Address findAddressById(int id);
	 @Select("SELECT * FROM STUDENTS WHERE STUD_ID=#{studId} ")
	 @Results({
	 @Result(id=true, column="stud_id", property="studId"),
	 @Result(column="name", property="name"),
	 @Result(column="email", property="email"),
	 @Result(property="address", column="addr_id",
//    使用内嵌sql也可以获取自定义字段详情
//    例如，一对一映射，使用注解@One实现： select属性指向一个方法名，从students表返回的addr_id将作为参数传递给这个方法
	 one=@One(select="com.mybatis3.mappers.StudentMapper.findAddressById"))
	 })
	 Student selectStudentWithAddress(int studId);


	@Insert("insert into students(name,email,addr_id, phone) values(#{name},#{email},#{address.addrId},#{phone})")
	@Options(useGeneratedKeys=true, keyProperty="studId")
	/**
	 * @Options(useGeneratedKeys = true, keyProperty = "instanceId", keyColumn = "instance_id",useCache = true, flushCache = false, timeout = 10000) ： 一些查询的选项开关，比如
	 * useGeneratedKeys 是否自动生成主键
	 * keyProperty 主键对应的属性
	 * keyColumn 代表主键字段名，默认为id
	 * useCache = true表示本次查询结果被缓存以提高下次查询速度，
	 * flushCache = false表示下次查询时不刷新缓存，
	 * timeout = 10000表示查询结果缓存10000秒
	 */
	void insertStudent(Student student);
	
	@Insert("insert into students(name,email,addr_id, phone) values(#{name},#{email},#{address.addrId},#{phone})")
	@Options(useGeneratedKeys=true, keyProperty="studId")
	void insertStudentWithMap(Map<String, Object> map);

	@Update("update students set name=#{name}, email=#{email}, phone=#{phone} where stud_id=#{studId}")
	void updateStudent(Student student);
	
	@Delete("delete from students where stud_id=#{studId}")
	int deleteStudent(int studId);


	@Select("select name ,count(1) as cnt from students group by name")
	List<Map<String, Integer>> countByName();

}
