/*
 * Copyright 2009 Michael Bedward
 *
 * This file is part of jai-tools.
 *
 * jai-tools is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * jai-tools is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with jai-tools.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package jaitools.media.jai.zonalstats;

import com.sun.media.jai.opimage.RIFUtil;
import com.sun.media.jai.util.ImageUtil;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.RenderedImage;
import java.awt.image.SampleModel;
import java.awt.image.renderable.ParameterBlock;
import java.awt.image.renderable.RenderedImageFactory;
import javax.media.jai.ImageLayout;
import javax.media.jai.ROI;
import javax.media.jai.RasterFactory;

/**
 * The image factory for the {@link ZonalStatsOpImage} operation.
 *
 * @author Michael Bedward
 */
public class ZonalStatsRIF implements RenderedImageFactory {

    /** Constructor */
    public ZonalStatsRIF() {
    }

    /**
     * Create a new instance of ZonalStatsOpImage in the rendered layer.
     *
     * @param paramBlock specifies the source image and the following parameters:
     * "stats", "band", "roi", "ignoreNaN", "nilValue"
     *
     * @param renderHints optional RenderingHints object
     */
    public RenderedImage create(ParameterBlock paramBlock, RenderingHints renderHints) {
        
        RenderedImage source = paramBlock.getRenderedSource(0);

        ImageLayout layout = RIFUtil.getImageLayoutHint(renderHints);
        if (layout == null) layout = new ImageLayout();

        ZonalStatistic[] stats =
                (ZonalStatistic[]) paramBlock.getObjectParameter(ZonalStatsDescriptor.STATS_ARG_INDEX);

        int band = paramBlock.getIntParameter(ZonalStatsDescriptor.BAND_ARG_INDEX);

        SampleModel sm = layout.getSampleModel(null);
        if (sm == null || sm.getNumBands() != stats.length) {

            int dataType = source.getSampleModel().getDataType();
            if (dataType != DataBuffer.TYPE_FLOAT && dataType != DataBuffer.TYPE_DOUBLE) {
                for (ZonalStatistic stat : stats) {
                    if (!stat.supportsIntegralResult()) {
                        dataType = DataBuffer.TYPE_DOUBLE;
                        break;
                    }
                }
            }

            sm = RasterFactory.createComponentSampleModel(
                    source.getSampleModel(),
                    dataType,
                    source.getWidth(), source.getHeight(), stats.length);

            layout.setSampleModel(sm);
            if (layout.getColorModel(null) != null) {
                ColorModel cm = ImageUtil.getCompatibleColorModel(sm, renderHints);
                layout.setColorModel(cm);
            }
        }

        ROI roi = (ROI) paramBlock.getObjectParameter(ZonalStatsDescriptor.ROI_ARG_INDEX);

        Boolean ignoreNaN =
                (Boolean) paramBlock.getObjectParameter(ZonalStatsDescriptor.NAN_ARG_INDEX);

        Number nilValue =
                (Number) paramBlock.getObjectParameter(ZonalStatsDescriptor.NO_RESULT_VALUE_ARG_INDEX);

        return new ZonalStatsOpImage(
                paramBlock.getRenderedSource(ZonalStatsDescriptor.DATA_SOURCE_INDEX),
                paramBlock.getRenderedSource(ZonalStatsDescriptor.ZONE_SOURCE_INDEX),
                renderHints,
                layout,
                stats,
                band,
                roi,
                ignoreNaN,
                nilValue);
    }
}

