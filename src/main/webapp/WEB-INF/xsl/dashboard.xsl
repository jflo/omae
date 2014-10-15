<?xml version="1.0" encoding="UTF-8" ?>
<!-- Character sheet based on the Shadowrun 5th Edition Character Sheet -->
<!-- Created by Keith Rudolph, krudolph@gmail.com -->
<!-- Version -500 -->
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:include href="ConditionMonitor.xslt"/>
	<xsl:template match="/characters/character">
	<table width="100%" cellspacing="0" cellpadding="0" border="0"
		class="tableborder">
		<tr>
			<td width="50%" style="text-align:center;">
				<strong>PHYSICAL DAMAGE TRACK</strong>
			</td>
			<td width="50%" style="text-align:center;">
				<strong>STUN DAMAGE TRACK</strong>
			</td>
		</tr>
		<tr>
			<td width="50%" valign="top">
				<xsl:call-template name="ConditionMonitor">
					<xsl:with-param name="PenaltyBox">
						<xsl:value-of select="cmthreshold" />
					</xsl:with-param>
					<xsl:with-param name="Offset">
						<xsl:value-of select="cmthresholdoffset" />
					</xsl:with-param>
					<xsl:with-param name="CMWidth">
						3
					</xsl:with-param>
					<xsl:with-param name="TotalBoxes">
						<xsl:value-of select="physicalcm" />
					</xsl:with-param>
					<xsl:with-param name="DamageTaken">
						<xsl:value-of select="physicalcmfilled" />
					</xsl:with-param>
					<xsl:with-param name="OverFlow">
						<xsl:value-of select="cmoverflow" />
					</xsl:with-param>
				</xsl:call-template>
			</td>
			<td width="50%" valign="top">
				<xsl:call-template name="ConditionMonitor">
					<xsl:with-param name="PenaltyBox">
						<xsl:value-of select="cmthreshold" />
					</xsl:with-param>
					<xsl:with-param name="Offset">
						<xsl:value-of select="cmthresholdoffset" />
					</xsl:with-param>
					<xsl:with-param name="CMWidth">
						3
					</xsl:with-param>
					<xsl:with-param name="TotalBoxes">
						<xsl:value-of select="stuncm" />
					</xsl:with-param>
					<xsl:with-param name="DamageTaken">
						<xsl:value-of select="stuncmfilled" />
					</xsl:with-param>
					<xsl:with-param name="OverFlow">
						0
					</xsl:with-param>
				</xsl:call-template>
			</td>
		</tr>
		<tr>
			<td class="rowsummary" colspan="2"> CONDITION
				MONITOR
			</td>
		</tr>
	</table>
	</xsl:template>
</xsl:stylesheet>