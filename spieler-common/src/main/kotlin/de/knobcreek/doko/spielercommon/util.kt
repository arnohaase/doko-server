package de.knobcreek.doko.spielercommon

/**
 * @author arno
 */
fun <T> firstNotNull(vararg candidates: () -> T?): T? {
    var result: T? = null

    for (candidate in candidates) {
        result = candidate()
        if (result != null) {
            return result
        }
    }

    return null
}