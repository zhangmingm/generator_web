package ${package.Mapper};

import ${package.Entity}.${entity};
import ${package.Vo}.${vo};
import ${superMapperClassPackage};
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Date;

/**
 * @author ${author}
 * @since ${date}
 * @Description: ${table.tableComment!} Mapper 接口
 */
@Repository
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

   /**
    * @author ${author}
    * @since ${date}
    * @Description: 多条件查询${table.tableComment}对象
    */
    public List<${entity}> select${entity}ListBySql(${entity} entity);

   /**
    * @author ${author}
    * @since ${date}
    * @Description: 根据SQL查询${table.tableComment}Vo对象集合
    */
    public List<${vo}> select${entity}VoListBySql(${entity} entity);


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 根据SQL添加${table.tableComment}对象。
    */
    public int insert${entity}BySql(${entity} entity);


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 根据SQL修改${table.tableComment}对象
    */
    public int update${entity}BySql(${entity} entity);




}
