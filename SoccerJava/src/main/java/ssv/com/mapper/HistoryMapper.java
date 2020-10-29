package ssv.com.mapper;

import org.apache.ibatis.annotations.Mapper;

import ssv.com.entity.History;

@Mapper
public interface HistoryMapper {

	void create(History history);

}
