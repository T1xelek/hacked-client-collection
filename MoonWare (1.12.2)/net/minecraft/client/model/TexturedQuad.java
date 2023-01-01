package net.minecraft.client.model;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec3d;
import optifine.Config;
import shadersmod.client.SVertexFormat;

public class TexturedQuad
{
    public PositionTextureVertex[] vertexPositions;
    public int nVertices;
    private boolean invertNormal;

    public TexturedQuad(PositionTextureVertex[] vertices)
    {
        vertexPositions = vertices;
        nVertices = vertices.length;
    }

    public TexturedQuad(PositionTextureVertex[] vertices, int texcoordU1, int texcoordV1, int texcoordU2, int texcoordV2, float textureWidth, float textureHeight)
    {
        this(vertices);
        float f = 0.0F / textureWidth;
        float f1 = 0.0F / textureHeight;
        vertices[0] = vertices[0].setTexturePosition((float)texcoordU2 / textureWidth - f, (float)texcoordV1 / textureHeight + f1);
        vertices[1] = vertices[1].setTexturePosition((float)texcoordU1 / textureWidth + f, (float)texcoordV1 / textureHeight + f1);
        vertices[2] = vertices[2].setTexturePosition((float)texcoordU1 / textureWidth + f, (float)texcoordV2 / textureHeight - f1);
        vertices[3] = vertices[3].setTexturePosition((float)texcoordU2 / textureWidth - f, (float)texcoordV2 / textureHeight - f1);
    }

    public void flipFace()
    {
        PositionTextureVertex[] apositiontexturevertex = new PositionTextureVertex[vertexPositions.length];

        for (int i = 0; i < vertexPositions.length; ++i)
        {
            apositiontexturevertex[i] = vertexPositions[vertexPositions.length - i - 1];
        }

        vertexPositions = apositiontexturevertex;
    }

    /**
     * Draw this primitve. This is typically called only once as the generated drawing instructions are saved by the
     * renderer and reused later.
     */
    public void draw(BufferBuilder renderer, float scale)
    {
        Vec3d vec3d = vertexPositions[1].vector3D.subtractReverse(vertexPositions[0].vector3D);
        Vec3d vec3d1 = vertexPositions[1].vector3D.subtractReverse(vertexPositions[2].vector3D);
        Vec3d vec3d2 = vec3d1.crossProduct(vec3d).normalize();
        float f = (float)vec3d2.xCoord;
        float f1 = (float)vec3d2.yCoord;
        float f2 = (float)vec3d2.zCoord;

        if (invertNormal)
        {
            f = -f;
            f1 = -f1;
            f2 = -f2;
        }

        if (Config.isShaders())
        {
            renderer.begin(7, SVertexFormat.defVertexFormatTextured);
        }
        else
        {
            renderer.begin(7, DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL);
        }

        for (int i = 0; i < 4; ++i)
        {
            PositionTextureVertex positiontexturevertex = vertexPositions[i];
            renderer.pos(positiontexturevertex.vector3D.xCoord * (double)scale, positiontexturevertex.vector3D.yCoord * (double)scale, positiontexturevertex.vector3D.zCoord * (double)scale).tex(positiontexturevertex.texturePositionX, positiontexturevertex.texturePositionY).normal(f, f1, f2).endVertex();
        }

        Tessellator.getInstance().draw();
    }
}