package com.example.util.svm

import com.google.cloud.nativeimage.features.NativeImageUtils
import com.oracle.svm.core.annotate.AutomaticFeature
import io.micronaut.core.annotation.Generated
import org.graalvm.nativeimage.hosted.Feature
import org.graalvm.nativeimage.hosted.Feature.BeforeAnalysisAccess


/** Registers data model packages for reflection with Substrate VM. */
@Generated
@AutomaticFeature
class ModelReflectionFeature: Feature {
    companion object {
        private val registeredPackages = sortedSetOf(
            "com.demo.model",
        )
    }

    override fun beforeAnalysis(access: BeforeAnalysisAccess) {
        registeredPackages.forEach {
            NativeImageUtils.registerPackageForReflection(
                access,
                it
            )
        }
    }
}
