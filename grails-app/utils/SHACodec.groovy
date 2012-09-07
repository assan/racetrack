
/**
 * Created with IntelliJ IDEA.
 * User: AJ
 * Date: 08.08.12
 * Time: 18:12
 * To change this template use File | Settings | File Templates.
 */
import java.security.MessageDigest

class SHACodec{
    static encode = { target ->
        MessageDigest md = MessageDigest.getInstance('SHA')
        md.update(target.getBytes('UTF-8'))
        return new String(md.digest()).encodeAsBase64()
    }
}