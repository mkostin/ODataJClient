<?xml version="1.0" encoding="UTF-8"?>
<!--
  Copyright 2013 Microsoft Open Technologies, Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:m="http://schemas.microsoft.com/ado/2007/08/dataservices/metadata" 
		xmlns:edmOld="http://schemas.microsoft.com/ado/2007/05/edm"
		xmlns:edm="http://schemas.microsoft.com/ado/2008/09/edm"
        version="1.0">

  <xsl:output method="text"/>

  <xsl:template match="edm:Schema">
    @Namespace("<xsl:value-of select="@Namespace"/>")

    <xsl:apply-templates select="edm:EntityType"/>
    <xsl:apply-templates select="edm:ComplexType"/>
    <xsl:apply-templates select="edm:EntityContainer"/>
    <xsl:apply-templates select="//edm:EntitySet"/>
  </xsl:template>

  <xsl:template match="edm:EntityType">
    @EntityType("<xsl:value-of select="@Name"/>")
    public class <xsl:value-of select="@Name"/> implements Serializable {
    <xsl:apply-templates select="edm:Key"/>
    <xsl:apply-templates select="edm:Property"/>
    <xsl:apply-templates select="edm:NavigationProperty"/>
    }
  </xsl:template>

  <xsl:template match="edm:Key">
    @Key
  </xsl:template>

  <xsl:template match="edm:ComplexType">
    @ComplexType("<xsl:value-of select="@Name"/>")
    public class <xsl:value-of select="@Name"/> implements Serializable {
    <xsl:apply-templates select="edm:Property"/>
    }
  </xsl:template>

  <xsl:template match="edm:Property">
    @Property(name = "<xsl:value-of select="@Name"/>", type = "<xsl:value-of select="@Type"/>", nullable = <xsl:value-of select="@Nullable"/>
    <xsl:if test="string-length(@m:FC_SourcePath) &gt; 0">, fcSourcePath = "<xsl:value-of select="@m:FC_SourcePath"/>"</xsl:if>
    <xsl:if test="string-length(@m:FC_TargetPath) &gt; 0">, fcTargetPath = "<xsl:value-of select="@m:FC_TargetPath"/>"</xsl:if>
    <xsl:if test="string-length(@m:FC_ContentKind) &gt; 0">, fcContentKind = <xsl:value-of select="@m:FC_ContentKind"/></xsl:if>
    <xsl:if test="string-length(@m:FC_KeepInContent) &gt; 0">, fcKeepInContent = <xsl:value-of select="@m:FC_KeepInContent"/></xsl:if>)
    private <xsl:value-of select="@Type"/><xsl:text> </xsl:text><xsl:value-of select="@Name"/>;
  </xsl:template>

  <xsl:template match="edm:NavigationProperty">
    @NavigationProperty(name = "<xsl:value-of select="@Name"/>", relationship = "<xsl:value-of select="@Relationship"/>",
    fromRole = "<xsl:value-of select="@FromRole"/>", toRole = "<xsl:value-of select="@ToRole"/>")
    private <xsl:value-of select="@Name"/><xsl:text> </xsl:text><xsl:value-of select="@Name"/>;
  </xsl:template>

  <xsl:template match="edm:EntityContainer">
    @EntityContainer(name = "<xsl:value-of select="@Name"/>", isDefaultEntityContainer = <xsl:value-of select="@m:IsDefaultEntityContainer"/>)
    public interface <xsl:value-of select="@Name"/> {
    <xsl:for-each select="edm:EntitySet">
      <xsl:value-of select="@Name"/> get<xsl:value-of select="@Name"/>();
    </xsl:for-each>

    <xsl:apply-templates select="edm:FunctionImport"/>
    }
  </xsl:template>

  <xsl:template match="edm:FunctionImport">
    @FunctionImport(name = "<xsl:value-of select="@Name"/>", entitySet = <xsl:value-of select="@EntitySet"/>.class,
    returnType = "<xsl:value-of select="@ReturnType"/>")
    <xsl:value-of select="@ReturnType"/><xsl:text> </xsl:text><xsl:value-of select="@Name"/>(
    <xsl:for-each select="edm:Parameter">
      @Parameter(name = "<xsl:value-of select="@Name"/>", type = <xsl:value-of select="@Type"/>, mode = <xsl:value-of select="@Mode"/>)<xsl:text> </xsl:text><xsl:value-of select="@Type"/><xsl:text> </xsl:text><xsl:value-of select="@Name"/>
      <xsl:if test="position() != last()">,</xsl:if>
    </xsl:for-each>
    );
  </xsl:template>

  <xsl:template match="edm:EntitySet">    
    @EntitySetName("<xsl:value-of select="@Name"/>")
    public interface <xsl:value-of select="@Name"/> extends EntitySet&lt;<xsl:value-of select="substring-after(@EntityType, '.')"/>, Integer&gt; {
    }
  </xsl:template>

</xsl:stylesheet>
