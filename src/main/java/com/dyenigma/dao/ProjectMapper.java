package com.dyenigma.dao;

import com.dyenigma.entity.Project;

import java.util.List;

public interface ProjectMapper extends BaseMapper<Project> {
    List<Project> getPrjByCoId(String coId);
}