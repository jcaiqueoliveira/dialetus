data class Version(
    val name: String,
    val major: Int,
    val minor: Int,
    val patch: Int,
    val code: Int,
    val buildId: String
)

object Versioning {
    private const val major = 1
    private const val minor = 0
    private const val patch = 0
    private const val bump = 0
    private val qualifier = Qualifier.PROD.nameQualifier()

    private val name = generateVersionName()

    init {
        require(bump <= 99)
        require(major <= 99)
        require(minor <= 99)
        require(patch <= 99)
    }

    private val actual by lazy {
        Version(name, major, minor, patch, generateVersionCode(), getBuildId())
    }

    @JvmStatic
    fun generateVersionCode(): Int {
        return (10 * 10000000) + (major * 1000000) + (minor * 10000) + (patch * 100) + bump
    }

    @JvmStatic
    fun generateVersionName(): String {
        return "$major.$minor.$patch$qualifier"
    }

    @JvmStatic
    fun getVersion() = actual

    @JvmStatic
    fun getBuildId(): String = System.getenv("BITRISE_BUILD_NUMBER") ?: "DEVELOPMENT"

    enum class Qualifier {
        INTERNAL {
            override fun nameQualifier(): String = "-INTERNAL"
        },

        ALFA {
            override fun nameQualifier(): String = "-ALFA"
        },

        BETA {
            override fun nameQualifier(): String = "-BETA"
        },

        RC {
            override fun nameQualifier(): String = "-RC"
        },

        PROD {
            override fun nameQualifier(): String = ""
        };

        abstract fun nameQualifier(): String
    }
}