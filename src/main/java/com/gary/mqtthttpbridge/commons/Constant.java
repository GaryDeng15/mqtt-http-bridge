package com.gary.mqtthttpbridge.commons;

/**
 *
 */
public class Constant {
    // ========== 调整：固定Redis Key（仅存最新一条） ==========
    public static final String REDIS_KEY_BATTERY_OVERALL_LATEST = "mqtt:battery:overall:latest";
    public static final String REDIS_KEY_CONTAINER_LATEST = "mqtt:container:latest";
    // Battery按batteryId分固定Key（每个电芯仅存最新一条）
    public static final String REDIS_KEY_BATTERY_PREFIX = "mqtt:battery:"; // 最终Key：mqtt:battery:latest:battery-1-1
    // Pack按packId分固定Key（每个Pack仅存最新一条）
    public static final String REDIS_KEY_PACK_PREFIX = "mqtt:pack:latest:"; // 最终Key：mqtt:pack:latest:pack1

    // ========== 其他常量不变 ==========
    public static final String REDIS_QUEUE_MYSQL_TASK = "mqtt:mysql:task:queue";
    public static final int BATCH_INSERT_SIZE = 50;
    public static final int MYSQL_THREAD_POOL_SIZE = 2;
}
