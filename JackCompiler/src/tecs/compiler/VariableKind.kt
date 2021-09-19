package tecs.compiler

enum class VariableKind {
    STATIC {
        override fun toSegmentName(): String = "static"
    },
    FIELD {
        override fun toSegmentName(): String = "this"
    },
    ARG {
        override fun toSegmentName(): String = "argument"
    },
    VAR {
        override fun toSegmentName(): String = "local"
    },
    NONE {
        override fun toSegmentName(): String {
            throw NotImplementedError()
        }
    };

    abstract fun toSegmentName(): String
}
