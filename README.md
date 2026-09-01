# Lilishop 电商平台（微服务拆分版）

基于 Lilishop 二次开发的 B2B2C 多商户电商平台，采用 Spring Cloud Alibaba 微服务架构，将原单体应用按业务域拆分为 8 个业务服务 + 网关 + 管理端聚合服务，配套买家端 / 商家端 / 管理端 / IM 四套前端。

## 技术栈

| 分层 | 技术 |
|---|---|
| 语言 / 基础框架 | JDK 21 · Spring Boot 4.0.5 |
| 微服务体系 | Spring Cloud 2025.1.3 · Spring Cloud Alibaba 2025.1.0.0 · Nacos 3.1.1（注册中心 / 配置中心，三节点集群） |
| 网关 | Spring Cloud Gateway（WebFlux，统一路由 / 鉴权 / 限流） |
| 数据层 | MyBatis-Plus 3.5.15 · MySQL · Druid 连接池 |
| 缓存 / 分布式锁 | Redis · Redisson（setIfAbsent + 令牌校验 + Lua 原子扣减） |
| 消息队列 | Redis List / ZSet 自研轻量队列（替代 RocketMQ，无额外中间件依赖） |
| 远程调用 | OpenFeign（内部服务走 /internal/** 契约） |
| 前端 | Vue 3.5 · Element Plus 2.14 · Vuex 4 · Vue Router 4 · axios |

## 模块结构

```
lilishop-master/
├── gateway/          # 网关（原 buyer-api 改造，端口 8888）
├── manager-api/      # 管理端聚合服务（端口 8887）：聚合查询 + 消息消费者 + 定时任务
├── im-api/           # IM 服务（端口 8885）：WebSocket 聊天
├── goods-service/    # 商品服务（端口 8891）
├── member-service/   # 会员服务（端口 8892）
├── order-service/    # 订单服务（端口 8893）：含购物车 / 交易 / 售后
├── promotion-service # 营销服务（端口 8894）
├── payment-service/  # 支付服务（端口 8895）：支付宝等支付插件
├── store-service/    # 店铺服务（端口 8896）
├── system-service/   # 系统服务（端口 8897）
├── statistics-service# 统计服务（端口 8898）
├── common-core/      # 公共核心（工具 / 消息模板 / 分布式锁）
├── common-api/       # 契约层（实体 / 枚举 / DTO / Feign 接口）
└── framework/        # 基础框架（安全 / 配置 / 日志等）

lilishop-ui-master/
├── buyer/            # 买家端
├── seller/           # 商家端
├── manager/          # 管理端
└── im/               # IM 端
```

## 核心设计

- **微服务拆分**：按业务域拆分（商品 / 会员 / 订单 / 营销 / 支付 / 店铺 / 系统 / 统计），公共契约沉淀至 common-api，内部调用走 OpenFeign `/internal/**`，管理端采用聚合模式减少跨服务请求。
- **支付链路**：支付宝回调 → 网关 → 支付服务验签 → Feign 通知订单服务 → 事务提交后发 Redis 队列 → 异步扣库存；四道幂等防线（预下单状态拦截、回调幂等检查、状态机校验、Redis 锁 + Lua 原子扣减）保障最终一致。
- **订单状态机**：9 态手动校验流转（UNPAID → PAID → UNDELIVERED → DELIVERED → COMPLETED，含 PARTS_DELIVERED / STAY_PICKED_UP / TAKE / CANCELLED），配套支付 / 交易 / 售后等 25 个状态枚举。
- **消息队列**：Redis List 实现发布订阅队列，ZSet 实现延迟消息（促销定时上下架），零额外中间件。

## 运行要求

- JDK 21、Maven 3.9+
- MySQL 8.x（导入 `lilishop.sql` 初始化，注意按需脱敏）
- Redis 6+（默认 6379）
- Nacos 集群（默认 8810 / 8820 / 8830 三节点）
- 前端：Node 16+，各端目录下 `npm install && npm run serve`

各服务具体配置见对应模块 `src/main/resources/application.yml`（Nacos 注册地址、数据源等请按本机环境调整，**请勿提交真实密钥与生产凭据**）。
