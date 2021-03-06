/*
 * FelicaCardReaderIOS.kt
 *
 * Copyright 2019 Google
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package au.id.micolous.metrodroid.card.felica

import au.id.micolous.metrodroid.card.Card
import au.id.micolous.metrodroid.time.TimestampFull
import au.id.micolous.metrodroid.card.TagReaderFeedbackInterface
import au.id.micolous.metrodroid.multi.Log
import au.id.micolous.metrodroid.multi.NativeThrows
import kotlinx.coroutines.runBlocking

import platform.Foundation.*

object FelicaCardReaderIOS {
    @NativeThrows
    fun dump(wrapper: FelicaTransceiverIOS.SwiftWrapper,
             defaultSysCode: NSData,
             feedback: TagReaderFeedbackInterface): Card {
        val xfer = FelicaTransceiverIOS(wrapper, defaultSysCode)
        Log.d(TAG, "Start dump ${xfer.uid}")
        return runBlocking {
            Log.d(TAG, "Start async")
            val df = FelicaReader.dumpTag(xfer, feedback)
            Card(tagId = xfer.uid?.let { if (it.size == 10) it.sliceOffLen(0, 7) else it }!!,
            scannedAt = TimestampFull.now(), felica = df)
        }
    }
    
    private const val TAG = "FelicaCardReaderIOS"
}
