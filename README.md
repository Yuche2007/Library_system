# 图书馆借书系统

一个基于Spring Boot和Vue的图书馆管理系统，支持用户借阅和管理员管理功能。

## 功能特性

- 用户端：图书浏览、搜索、借阅申请
- 管理端：图书CRUD管理、借阅记录管理
- 超级管理员：用户和权限管理
- 基于RBAC的权限控制模型
- JWT认证授权

## 项目结构
- 后端项目文件夹：library-server
- 前端项目文件夹：library-ui
- Readme.md
- Library_Mysql.sql

## 技术栈
- 后端开发采用IDEA工具
- 前端开发采用WebStorm
- AI生成工具采用IDEA Plugins: 阿里云通义灵码

### 后端
- Spring Boot 2.7
- Spring Security + JWT
- MyBatis-Plus
- MySQL 5.7.44.0

### 前端
- Vue 3
- Element Plus
- Axios

## 环境要求

- JDK 21
- MySQL 5.7.44.0
- Node.js v24.7.0
- Maven 3.9.11

## 快速开始

### 数据库配置
- sql文件：Library_Mysql.sql

### 后端启动
- CMD
#### 克隆项目
- git clone https://github.com/Yuche2007/library-system.git
#### 进入后端目录
- cd library-system/backend
#### 修改数据库配置
- application.yml 中配置数据库连接
#### 编译并运行
- mvn clean install mvn spring-boot:run

### 前端启动
- CMD
#### 进入前端目录
- cd library-system/frontend
#### 安装依赖
- npm install
#### 启动开发服务器
- npm run serve
