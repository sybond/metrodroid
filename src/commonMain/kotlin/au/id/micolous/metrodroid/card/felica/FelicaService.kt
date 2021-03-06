/*
 * FelicaService.kt
 *
 * Copyright 2011 Eric Butler <eric@codebutler.com>
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

import au.id.micolous.metrodroid.multi.FormattedString
import au.id.micolous.metrodroid.serializers.XMLListIdx
import au.id.micolous.metrodroid.ui.ListItem
import au.id.micolous.metrodroid.util.NumberUtils
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class FelicaService(
        @XMLListIdx("address")
        val blocks: List<FelicaBlock>) {
    fun getBlock(idx: Int) = blocks[idx]

    @Transient
    val rawData: List<ListItem>
        get() =
            blocks.mapIndexed { blockAddr, (data) ->
                ListItem(
                        FormattedString(NumberUtils.zeroPad(blockAddr, 2)),
                        data.toHexDump())
            }
}
