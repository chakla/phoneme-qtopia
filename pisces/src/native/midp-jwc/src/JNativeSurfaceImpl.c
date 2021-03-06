/*
 * 
 * Copyright  1990-2009 Sun Microsystems, Inc. All Rights Reserved. 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER 
 *  
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU General Public License version 
 * 2 only, as published by the Free Software Foundation. 
 *  
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
 * General Public License version 2 for more details (a copy is 
 * included at /legal/license.txt). 
 *  
 * You should have received a copy of the GNU General Public License 
 * version 2 along with this work; if not, write to the Free Software 
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 
 * 02110-1301 USA 
 *  
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa 
 * Clara, CA 95054 or visit www.sun.com if you need additional 
 * information or have any questions.
 */

#include <JAbstractSurface.h>

#include <KNIUtil.h>
#include <midpPiscesUtils.h>

#include <PiscesUtil.h>
#include <PiscesSysutils.h>
#include <commonKNIMacros.h>

#define SURFACE_NATIVE_PTR 0
#define SURFACE_LAST SURFACE_NATIVE_PTR

#define GRAPHICS_TRANSX 0
#define GRAPHICS_TRANSY 1
#define GRAPHICS_LAST GRAPHICS_TRANSY


static jboolean fieldIdsInitialized1 = KNI_FALSE;
static jfieldID graphicsFieldIds[GRAPHICS_LAST + 1];

static jfieldID fieldIds[SURFACE_LAST + 1];
static jboolean fieldIdsInitialized = KNI_FALSE;

static jboolean initializeSurfaceFieldIds(jobject objectHandle);
static jboolean initializeGDFieldIds();

KNIEXPORT KNI_RETURNTYPE_VOID
Java_com_sun_pisces_NativeSurface_clean() {
    KNI_StartHandles(1);
    KNI_DeclareHandle(objectHandle);

    AbstractSurface* surface;
    
    KNI_GetThisPointer(objectHandle);
    initializeSurfaceFieldIds(objectHandle);

    surface = (AbstractSurface *) (long) KNI_GetLongField(objectHandle, fieldIds[
                                                            SURFACE_NATIVE_PTR])
                                                            ;
    if (surface != NULL) {
        if (surface->super.data != NULL) {
            memset(surface->super.data, 0, surface->super.width *
                                           surface->super.height *
                                           sizeof(jint));
        }
    }

    KNI_EndHandles();
    KNI_ReturnVoid();
}

KNIEXPORT KNI_RETURNTYPE_VOID
Java_com_sun_pisces_NativeSurface_draw() {
    AbstractSurface* surface;  
    int w, h, offset, xx, yy, x, y;
    int soffset;
    unsigned short rgb;
    int srgb;
    int r, g, b;
    int dr, dg, db;
    int sr, sg, sb;
    int sa, sam;
    float opacity;

    gxj_screen_buffer *sbuf;
    gxj_screen_buffer screen_buffer;

    java_graphics * gr;
    
    KNI_StartHandles(2);
    KNI_DeclareHandle(destinationHandle);
    KNI_DeclareHandle(objectHandle);
    
    opacity = KNI_GetParameterAsFloat(6);
    h = KNI_GetParameterAsInt(5);
    w = KNI_GetParameterAsInt(4);
    y = KNI_GetParameterAsInt(3);
    x = KNI_GetParameterAsInt(2);

    KNI_GetParameterAsObject(1, destinationHandle);
    KNI_GetThisPointer(objectHandle);
    
    initializeSurfaceFieldIds(objectHandle);
    initializeGDFieldIds();
    
    gr = GXAPI_GET_GRAPHICS_PTR(destinationHandle);
    
    if (gr != NULL) {
            sbuf = gxj_get_image_screen_buffer_impl(
                        (gr != NULL && gr->img != NULL)?gr->img->imageData:NULL, 
                        &screen_buffer, destinationHandle);
             sbuf = (gxj_screen_buffer *)getScreenBuffer(sbuf);
    }    
    

    x += KNI_GetIntField(destinationHandle, 
                            graphicsFieldIds[GRAPHICS_TRANSX]);
    y += KNI_GetIntField(destinationHandle, 
                          graphicsFieldIds[GRAPHICS_TRANSY]);
    
    surface = (AbstractSurface *) (long) KNI_GetLongField(objectHandle, fieldIds[
                                                           SURFACE_NATIVE_PTR]);
    

    if (surface != NULL && surface->super.data != NULL && opacity != 0) {
        if (sbuf->alphaData != NULL) {
            for(yy = 0 ; yy < surface->super.height; yy++) {
                for(xx = 0; xx < surface->super.width; xx++) {
                    soffset = yy * surface->super.width + xx;
                    sa = (int) ((*((int *) surface->super.data + soffset) & 
                                            0xff000000) >> 24) * opacity + 0.5;
                    sam = 255 - sa;
                    if (sa != 0) {
                      offset = (yy + y) * sbuf->width + xx + x;
                        
                        srgb = *((int *) surface->super.data + soffset);
    
                        rgb = *((unsigned short *) sbuf->pixelData + offset);
                        
                        r =  rgb >> 11;
                        g = (rgb >> 5) & 0x3f;
                        b =  rgb &  0x1f;

                        sr = (srgb >> 19) & 0x1f;
                        sg = (srgb >> 10 ) & 0x3f;
                        sb = (srgb >> 3) & 0x1f;
                        
                        dr = (sr * sa + sam * r) / (255);
                        db = (sb * sa + sam * b) / (255);
                        dg = (sg * sa + sam * g) / (255);
                         
                        *((unsigned short *) sbuf->pixelData + offset) = 
                         ((dr & 0x1f) << 11) + ((dg & 0x3f) << 5) + (db & 0x1f);
                        *((unsigned char *) sbuf->alphaData + offset) = sa;
                    }                        
                }
            }
        } else {
            for(yy = 0 ; yy < surface->super.height; yy++) {
                for(xx = 0; xx < surface->super.width; xx++) {
                    soffset = yy * surface->super.width + xx;
                    sa = (int) ((*((int *) surface->super.data + soffset) & 
                                            0xff000000) >> 24) * opacity + 0.5;
                    sam = 255 - sa;
                    if (sa != 0)
                      {
                        offset = (yy + y) * sbuf->width + xx + x;

                        srgb = *((int *) surface->super.data + soffset);
    
                        rgb = *((unsigned short *) sbuf->pixelData + offset);
                        
                        r =  rgb >> 11;
                        g = (rgb >> 5) & 0x3f;
                        b =  rgb &  0x1f;

                        sr = (srgb >> 19) & 0x1f;
                        sg = (srgb >> 10 ) & 0x3f;
                        sb = (srgb >> 3) & 0x1f;
                        
                        dr = (sr * sa + sam * r) / (255);
                        db = (sb * sa + sam * b) / (255);
                        dg = (sg * sa + sam * g) / (255);
                         
                        *((unsigned short *) sbuf->pixelData + offset) =                                                               ((dr & 0x1f) << 11) + ((dg & 0x3f) << 5) + (db & 0x1f);
                    }                        
                }
            }
        } 
    }
    KNI_EndHandles();
    KNI_ReturnVoid();    
}

static jboolean
initializeGDFieldIds() {
    static const FieldDesc graphicsFieldDesc[GRAPHICS_LAST + 2] = {
                                                       { "transX", "I" },
                                                       { "transY", "I" },
                                                       { NULL, NULL}
                                                      };            
    jboolean retVal;

    if (fieldIdsInitialized1) {
        return KNI_TRUE;
    }
    
    retVal = KNI_FALSE;
    KNI_StartHandles(1);
    KNI_DeclareHandle(graphicsHndl);
    KNI_FindClass("javax/microedition/lcdui/Graphics", graphicsHndl);
    if (!KNI_IsNullHandle(graphicsHndl) && 
        initializeFieldIds(graphicsFieldIds, graphicsHndl, graphicsFieldDesc)) {
        retVal = KNI_TRUE;
        fieldIdsInitialized1 = KNI_TRUE;
    }
    KNI_EndHandles();
    return retVal;
}

static jboolean
initializeSurfaceFieldIds(jobject objectHandle) {
    static const FieldDesc surfaceFieldDesc[] = {
                { "nativePtr", "J" },
                { NULL, NULL }
            };

    jboolean retVal;

    if (fieldIdsInitialized) {
        return KNI_TRUE;
    }

    retVal = KNI_FALSE;

    KNI_StartHandles(1);
    KNI_DeclareHandle(classHandle);

    KNI_GetObjectClass(objectHandle, classHandle);

    if (initializeFieldIds(fieldIds, classHandle, surfaceFieldDesc)) {
        retVal = KNI_TRUE;
        fieldIdsInitialized = KNI_TRUE;
    }

    KNI_EndHandles();
    return retVal;
}
