package com.ritier.my_memo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ritier.my_memo.Util.getBinaryFromBitmap
import com.ritier.my_memo.Util.getBitmapFromBinary
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

@RunWith(AndroidJUnit4::class)
class BinaryTest {
    private  var bitmap: Bitmap? = null
    lateinit var binary: String

    fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            val url = URL(src)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) { // Log exception
            null
        }
    }

    @Before
    fun setData() {
        val url = "https://firebasestorage.googleapis.com/v0/b/rococodish.appspot.com/o/%ED%94%84%EB%A1%9C%ED%95%84%EC%82%AC%EC%A7%84%2F193c23bd-9926-358b-809c-f683099dc5a4?alt=media&token=c27eee51-1a09-4bae-b8a1-9e23947e85b6"
        bitmap = getBitmapFromURL(url)
        binary = "/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAFA3PEY8MlBGQUZaVVBfeMiCeG5uePWvuZHI////////\n" +
                "    ////////////////////////////////////////////2wBDAVVaWnhpeOuCguv/////////////\n" +
                "    ////////////////////////////////////////////////////////////wAARCAHCAcIDASIA\n" +
                "    AhEBAxEB/8QAGQABAQEBAQEAAAAAAAAAAAAAAAECAwQF/8QAKRABAQEBAAICAgEEAgIDAAAAAAER\n" +
                "    AiExAxJBURMyYXGBBCIUQjNSof/EABcBAQEBAQAAAAAAAAAAAAAAAAABAgP/xAAWEQEBAQAAAAAA\n" +
                "    AAAAAAAAAAAAARH/2gAMAwEAAhEDEQA/APLoCNmmgAAAAAAAAAIoAICgAACAAGAAACgAgAAABoAA\n" +
                "    gAqAAAAAAAAACoaAACKAIKAgCoAA2Jqo0BoAGgAACKAIoCCgIKAgoCCgJhigJgoCCgIKgCKAgoAi\n" +
                "    gIKCIAAAAAAAAAoAIAAAAAACKAgoo0gqKgAK6fHluWOTp8X9UEd/4Z+kvwyfh6J6SzVR4+vjxj62\n" +
                "I/System.out: PZ1xsceuMQ1wR1vMrnZgqAYKAAKgAAAuoAaaALpqALpqAGiKIACgAAAAAgAAAAAAGgAAIKAgoCAK\n" +
                "    ACAAo0LhjKsquGKMuvw/1OeN/FP+wle+BPMRUPbHXLep7EefvjPLk9Xc1w65yorlZlR0sZ+grAuI\n" +
                "    KAvsEHSfHrX8QjiuO0+KLOMBw+tMuPR9YfWIPNhj0fSH0ijzjtfjifxf3ByR1vxf3Zvx2AwNfWn1\n" +
                "    oIi5UAFQAAAAAAAAAAAAAAAAAAAAAAHUUYaQVMUGuP6vCYvHtYle3n1Eq8/0xmtMEqsLKC3y59cu\n" +
                "    qWIPL1z5TMdrz5c7MFZklS8RqLZqK5XheONrWunMwFkwLcQFtTQAC+1BIoAmItZoKl9KAmQsjUAY\n" +
                "    +sS/HK6JQc/42b8TrpFHG/HSfF1XeVudQR558HSz/j9V6pY2DyT/AI1L/wAex62ao8l+HqMX4+p+\n" +
                "    Huk0+sB4p8XX6P4uns+sX6wHh/j6/SfTr9PdeYn1iDxfTr9H06/T2/WH1ijw/W/ox7frP0zfj5/Q\n" +
                "    PJlTHs+nOZi/xcX8A8Q9d+Hln+DkHmHo/ggCYYDm2YigJiz2APX8d3mL1HP4L4x1rbDmmrYmWKjU\n" +
                "    rX4c5crpLoqWOfXOurPUQeezB1sjF8oOc52uh6MFPakAAARUAJVRAW+UsxZ7AIYAKCUBKqAmCpfN\n" +
                "    AW1AGp1jc7c57UHX76smuOunPfhUdPSWn21KAqG4Cs2yJ13M8Ofmg3e/0z9qvPLU4BjzTy6WYfhR\n" +
                "    y8tTo6jPE8oOm6LiaAIA5CGsNgmroAamg6/DZ9noeTm5Xrl2Nxms30x6dLGKrKVebifg+wrr4qXw\n" +
                "    zLsZ66QTqsVaiKYsAFABFwAT8otRAT8gopPKGg0gQF1AA3yioAGIAAC7hrKgWrzfLFTcEd5dX7OX\n" +
                "    HXhdB0+8Y661lZAJHWcpI0ov4PSazaqNJb5SLFEvpltnNQWXW8STF1FAAeZAYbAMAAFI9Xw3Y8rt\n" +
                "    8PWWRYzXosYsbrLbLF8MWyunXpyxBZciXyCKjX4RYBAWwEUwzARFKDNRfYghvkIoSn5RZcBRCAuE\n" +
                "    EAD/ACaBUFBBFBEVASpIq8iLPEazSRYBmtc8+Ukbn/VRfTG2rbrO4qLvgTdAWVqMSOkA69M8r1fw\n" +
                "    SAUi2EiKC4A8qg5uiAKAADXx/wBUZb+P+qLEr1y6x1V3w59XVYLWPyeQUCALiyErUgEhnhqTVkMG\n" +
                "    MLG7GbAYs8s322zQRFzyiCHo/BQEi4AB+CgET8ChUi0gLqULQNQAEqpQRYizwDpz6bkY49OkVCRO\n" +
                "    us9NVzvtUTQwBTBcAkbniJAGem4zVlRWkUxQAQeZGNNZxttGdNBtGdq/YxWnT4p5lcp1+3T47ZYM\n" +
                "    12vX6Yq1FQBZEEaFBGuTBRtfaT+zSoyz03WOkGb5rHqtp7iKyyt8VFE9Kfg/CAigIYq/hRn0eWsT\n" +
                "    zoMquAIjWYgIigJWWkBFRQb4rpK4x0+yxlb0yprQhDRBr8CZvtrmeAXBU9AnvpcXmNCuctjcupYz\n" +
                "    6QbGPIDy2IqI2AAAuAvPOu/PORz+Pm76ejBllG7GcBI0npfCCrPKRqRRcMX0n2n7EJ49tM+zm54U\n" +
                "    arNarHSDPSfhqs32Klm6z+Gr+kBFxJ6UEwnhfyl20Bfa5q5oiJjeeRRjD01fLOIJiY1YgMUVkDUU\n" +
                "    FSotQFjUrMVUaltMtXlaqJmElPNagLzG5GY0CbjPuravPoFnhUVFSotQEDQHlTFGXREUVDGpGcal\n" +
                "    Cu3xcu2M/FPDpissWJjphiYOWGOmQ+sBz9H3nM8tXmsfJznAMdf8nnf6az/5M/8Ar/8ArlzzPt1v\n" +
                "    4a+P4+bu/hrEd/j+bnr06c9a8nfM55+3Mzy38fyXwlHrtZpKIqVFrN8Al8J+VqABigfkw/KyASNZ\n" +
                "    iyLnhUTGbGqzRDP0mYvpM/YrN8o1WbUGai1BU/KL7MBEXAEaTwSg1uLNtZa5qo6STBnbfRtgjcW1\n" +
                "    j7VLbihbtdeZkcefNd54iKAAmJVtZAAB5kMMZbAwwCN8zWMb+P8ASxK9PxTw6sfHPDorKYKlUSoa\n" +
                "    msisfPN4rZ1NnlR8/q3nq/qrOsv9q6fJ8eOX0Ua+T5JePrE+P+rGbzdd/i+OTzUpHaVrfDM8Q1FV\n" +
                "    Kfk9glZ/LX5QEnpcM1RE/LU8Jyug3C1n7M26o111GZv7Zan+AWeBQGazY3WLKgziY1qColq1KCWo\n" +
                "    qCH+g00UWIojfNxrXONSqNXwzb4XWb5BePbvHHnluWwGxJdUGamNVATBcAeZRGHQAAb49sOnxcy1\n" +
                "    YzXq59NJJkVtgSrURWUxpEEVKnkVOvLjeXWpgOfPEn48txcwBDQoLF9sqCX2QNAJ/dE3yqNEqW6z\n" +
                "    9gavTP2ceu7q/Fxfl6zcxR0/kmn80z2x1x9Ze5fTl9bm54Eevnv7f01udfivBLefMuOvHzfjvyD1\n" +
                "    +Pwx14Y5+SX1W9ZVmioKlSrUBmo1WbAPyuJh/sRRFAjTKg0Z5I3xMUakwwVRPRqVZNQKvPgwBoZ0\n" +
                "    UeU0HN0AAHX4v7XHJ0+LqS+VjNeqbi7DmywxthUTzFl0URUqCVmr7XAYwasZqKzUKyC+iehAa3wg\n" +
                "    oEqLmJfKomooBLM8udvlv/LFgOPXt1/43X166t9Yx1PNZ8+t8Ki3q9ePwt+S34/ozJZfPpkFtQAa\n" +
                "    58eXo5u8uPPNv4ejjj/riKmqlllX/SCM1pmggqUUCKIzi4oAAo1z7dZPDnx7dfwCCtSAzOVVAEq4\n" +
                "    mAguAPMIMOgaAC8+0yk39Klez48vLf8AiuPw2u7bCb+0z9NM+Z/eCJ9v2e0vn0c7nlFaxK0lgM6z\n" +
                "    VqYDOJWvB7BzpG8PqDONSNTlrAc7Kw69Rj6gyLgDKNWJgMXlPpHTCSAz/HP0n8LsuYDhPg321P8A\n" +
                "I/System.out: jye3bwAzPjnMX0tvhnQX2ZouoMYfVpcBz+qY65/Zm8qOeYY1hgMYY0AyC8wG+I6JxPDcmASAAAgK\n" +
                "    hqUF0YAcETYaw6KGmgpN/abDx+1R2+Lqy5j0Sx4pXq+PrZ5ajNdBE/2ISNSJGgEqufXf1l1US+GL\n" +
                "    24fJ8vVviuV66/dB6vss6jx/e/tqd39g9cutSvNO/Xl0568ewd4rHNdICYzeXRMBysZrrZ4YsRWK\n" +
                "    jVjIBINcwBoyACaaAICCiaqixYkagCVpFRjEsbQGLGa6YxUGMdOInM2u0mCrzMis6suguIooiKIM\n" +
                "    rRKAIA8mBhuMtrmmRN1QNk/Gr9r+IiA19r+2/j+TLJXMEe2dTr1VcPh6kd5daZaioqoz08vzdXf7\n" +
                "    PR8lyPH8t8gkxOuZ+GNanYM2Mul8yYfS31AY9Nc/JYnXFjIPV8fe+dernqV83m5Xq4+SUHpGeLrY\n" +
                "    IzY2zQY65c8drGLAc8ai4sRVMJYYqCZinlFRFAQxQFiosVFZ1UoGmkAZYrdZvsF+PNdWeOcaRTGW\n" +
                "    0wCVZdZAaE1QTEsUBnBQHiMBl0VP9hgLqQw8CE2tTnz+/wDDfHx2zz4jpzzOfUUZ446nvxHWdMio\n" +
                "    7zyVz+Pr8Ois1z+R5OvOx6+3i+TeehGPrYjX3uM0Fl/Z9rIhYC3q1OrvlAFjp8fmzHOTXo+HnLNB\n" +
                "    6vjnhtJ6UBBASs+WvLIJ9SRfKf7Br8CRQMABEUBFwyKAKAlZvtpFDQQErMm1bWuIg3IoIoioCWkM\n" +
                "    TQU3EUF3Ss+ll0AUB4QGWwWTfEdePinvoHPmXrxHXn4pz781ueJkVUTFAEwVANyuvPWxxrP2vPlU\n" +
                "    rv083y/Hr0cX7c6nU2Ky8FmJXq6+PY8vUy4Asv7QBucS3xU+s1JK6cc4Bzw9HEzGOZ5jrJmKOkVn\n" +
                "    cN2IKVMANSriUGdRTAFTWtA0TQFEwBQigFEAEpqoVnVtZRTNrpJ4SRpFAUAQATFQEUAMMAEFwB4v\n" +
                "    bXPFt9eHTj48810Rpmczn1F1UAAA1WVBdRWOr+J7EXqnPG+ejji7vToKnrxD7YqCFvN8PP8APxP/\n" +
                "    AFjtYzi6mMT4uc8Q+s8+HWeFyX0o5/WEjX1Wf4EXieP7tyM8tT+4NQiEBU39CWgus3z+T7ecZt/Y\n" +
                "    G38hqeLPANHpnbPZug1oigqougKhoAgAmlrFoLavM1yt8unx9IrqABFRQAARFAEoewE0KBozoois\n" +
                "    TqtTplpcDVVEStIgyLWb4Bbci8c/m+045u7XTBQVAQARmo0lgJE9K59XfCjfPdrpKxzz4aw1FxZW\n" +
                "    Ulpo66luOf28Jer/AKUdL0x18kntyvV68c+2uefHn2hhe/tPEZvfU/DpJheRccJ8tldObv8AhOuD\n" +
                "    45ZVR1kX6/onhqCIKgGn+VsTQPSsm4DSWs2sddA1esc+rpaiCa3xcYpKK9crWOXF100BUAUSoCoA\n" +
                "    AntQSpVrIGBoDJmqI0ztjU61EvjzAdErPPX4rSojM/7X+x1W+ecRWopFwRBQGai1ARFZ66yAnVw5\n" +
                "    4y7Tnm27W8A00xAXUE0Cs9X8LanE+3X2/Chx8f1baQVErSAxV49pV4/qEroIqsiooIYCiembTqsW\n" +
                "    oFrNpUQABTAQHf47jr7jz8dPRzQPSylQFLElXdBIYqChqAiWs0tZ7v0nkGv9jz/y0B6RRFTExpLB\n" +
                "    XOs3u8ulT6ywGeL9rtdo5fXPSzqwHZZXOdytwRpENUSotRBm1njnz9r+S711k9OsmQUkMUERK1iU\n" +
                "    Gax+W6x1+gST7df2dJMOeZzGhUxGkBk1azVRKvxzxrNrrxM4ipVwVNED0zemb0DVrF6S9MUFtZtX\n" +
                "    8IigAAqAIqAc3y9Xx+nkej4rsB2CAJYjTN9AsohoDPVarGbfIHM/NcPn62u/y9ZxjydXaKyLgD3A\n" +
                "    YgAAzYRakFMZxssBhZ1YIDf3p9mTAa+2pbkYsv4Tm29efwDtxzkaSVZRFQ00BKupaDHVTiedqdXe\n" +
                "    pHSQVQQRUNASs1pmqM5vWO1sxjie6tEL2z9kQQtZq1BRBQRFqAKQAsRpBErNarNFHb4b+HFv4r/2\n" +
                "    B6pVSAGgn5AT2VrmZAT64jVY6uQHH5uvbhGvku1kVRAR7pRz5uOkuooACI0zQaMTmqDNIuGCgoDN\n" +
                "    jOeW6gIu2GLgJ9k+2ri4DO1nrvHTE64nQMfH5v2rtHOT6zGvsDeJhOoojLNaqUGZSlT2o6c+OYla\n" +
                "    9RmjLNnlGkwGUxrEsFZsRrDARMXARMUAEqs0EqLfCCjXx+OmTcoPZzfCuXx9bHSXQWs6u+CTfIHM\n" +
                "    aRQSuPy9O1vjXl+Trao5VmqgoICPX+VlxGmVbl0rG43LoIKgJ6rbNJQaxMaUGBrEBExUFFRcAxcM\n" +
                "    AMFQEsZsbQGPSzpcZEb1KzuNfhRirx5qdOnEyCL+Ga1UojIUgJiVvMSxRgNxZ5BExpKCVFSoqJSo\n" +
                "    AioAlrTNgN/F35ejfDxy5Xo460Hbma2zz6UEFZoM/Jc4eXqu/wA3XjHmtUZqLUAAB61lZWMq1hLl\n" +
                "    VAbl0xieG5dBBUBZWpWAHRGZ019oCYYuxfAM4uKAi4LoJiY0lBMRpMBmstpQYrO5W7GKovt2ceP6\n" +
                "    pHYZGelYvtRZNLFi1BJ5LE9Ko59c6xt5uO1c+pKgaMeYugqJpopWbFQQQXwKiVUtBl0+PrOnMnsH\n" +
                "    u5uxpy+O7y6A0zV1jvrIDh83W1xrXV2sUEBFAAHrAZVuKAIT2ANwoAgAoAIoALCgB+AAWFAUAEKg"
    }

    @Test
    fun testBinaryFromBitmap() {
        print(getBinaryFromBitmap(bitmap!!))
        Assert.assertEquals(true, getBinaryFromBitmap(bitmap!!).length> 10)
    }

    //용량 압축과 이미지 형식변환 때문에 bitmap과 binary는 같을 수 없음.
    @Test
    fun testBitmapFromBinary(){
        Assert.assertEquals(true, getBitmapFromBinary(binary) != null)
    }

    @After
    fun done(){
        print("이미지 변환 테스트를 수행하였습니다.")
    }
}