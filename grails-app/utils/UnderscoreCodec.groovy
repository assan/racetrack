
/**
 * Created with IntelliJ IDEA.
 * User: AJ
 * Date: 08.08.12
 * Time: 18:08
 * To change this template use File | Settings | File Templates.
 */


class UnderscoreCodec {
    static encode = { target->
        target.replaceAll(" ","_")

    }
    static decode={  target ->
        target.replaceAll("_"," ")
    }
}