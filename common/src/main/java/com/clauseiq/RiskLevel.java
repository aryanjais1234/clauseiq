package com.clauseiq;

/**
 * The order of these values matters.
 * We use ordinal() to find the highest risk in a contract,
 * so please keep them from lowest to highest.
 */
public enum RiskLevel {
    NONE,
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}