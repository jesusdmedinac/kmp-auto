package io.github.kmpauto.core

/**
 * Foundation definition for kmp-auto platform diagnostics and versioning.
 */
public object KmpAuto {
    public const val VERSION: String = "0.1.0-SNAPSHOT"
}

public expect val platformName: String
