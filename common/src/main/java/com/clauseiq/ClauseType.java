package com.clauseiq;

/**
 * The AI must pick exactly one of these for every clause.
 * If it does not fit anywhere, it should pick OTHER.
 */
public enum ClauseType {
    TERMINATION,
    AUTO_RENEWAL,
    LIABILITY,
    INDEMNITY,
    PAYMENT_TERMS,
    CONFIDENTIALITY,
    INTELLECTUAL_PROPERTY,
    DATA_PROTECTION,
    GOVERNING_LAW,
    SERVICE_LEVEL,
    OTHER
}