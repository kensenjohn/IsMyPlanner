<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.1"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:fo="http://www.w3.org/1999/XSL/Format"
        exclude-result-prefixes="fo">
    <xsl:template match="/invoicePdfBean">
    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
        <fo:layout-master-set>
            <fo:simple-page-master master-name="my-page">
                <fo:region-body margin="1in"/>
            </fo:simple-page-master>
        </fo:layout-master-set>

        <fo:page-sequence master-reference="my-page">
            <fo:flow flow-name="xsl-region-body">
                <fo:block>
                        <fo:table>
                            <fo:table-column column-width="65%"/>
                            <fo:table-column column-width="35%"/>

                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell >
                                        <fo:block>
                                            <fo:external-graphic content-width="100%" content-height="60" >
                                                <xsl:attribute name="src">
                                                    url('<xsl:value-of select="logo"/>')
                                                </xsl:attribute>
                                            </fo:external-graphic>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell >
                                        <xsl:if test="vendorName!=''">
                                            <fo:block font-size="9" font-weight="bold"><xsl:value-of select="vendorName" /></fo:block>
                                        </xsl:if>
                                        <xsl:if test="vendorPhone!=''">
                                            <fo:block font-size="9"><xsl:value-of select="vendorPhone" /></fo:block>
                                        </xsl:if>
                                        <xsl:if test="vendorAddress1!=''">
                                            <fo:block font-size="9"><xsl:value-of select="vendorAddress1" /></fo:block>
                                        </xsl:if>
                                        <xsl:if test="vendorAddress2!=''">
                                            <fo:block font-size="9"><xsl:value-of select="vendorAddress2" /></fo:block>
                                        </xsl:if>
                                        <fo:block font-size="9">
                                            <xsl:if test="vendorCity!=''">
                                                <xsl:value-of select="vendorCity" />
                                            </xsl:if>&#160;&#160;
                                            <xsl:if test="vendorState!=''">
                                                <xsl:value-of select="vendorState" />
                                            </xsl:if>
                                        </fo:block>
                                        <xsl:if test="vendorZip!=''">
                                            <fo:block font-size="9">
                                                <xsl:value-of select="vendorZip" />
                                            </fo:block>
                                        </xsl:if>
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>

                        </fo:table>
                </fo:block>
                <fo:block space-before="8mm"  border-bottom-style="solid" >
                    <fo:block font-size="14">Invoice</fo:block>
                </fo:block>
                <fo:block space-before="8mm"  space-after="8mm" padding-after="5mm">
                    <fo:table>
                        <fo:table-column column-width="60%"/>
                        <fo:table-column column-width="40%"/>

                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell >
                                    <fo:block>
                                        <fo:block font-size="9" ><xsl:value-of select="clientFirstName" />&#160;&#160;<xsl:value-of select="clientLastName" /> </fo:block>
                                        <xsl:if test="clientPhone!=''">
                                            <fo:block font-size="9"><xsl:value-of select="clientPhone" /></fo:block>
                                        </xsl:if>
                                        <xsl:if test="clientAddress1!=''">
                                            <fo:block font-size="9"><xsl:value-of select="clientAddress1" /></fo:block>
                                        </xsl:if>
                                        <xsl:if test="clientAddress2!=''">
                                            <fo:block font-size="9"><xsl:value-of select="clientAddress2" /></fo:block>
                                        </xsl:if>
                                        <fo:block font-size="9">
                                            <xsl:if test="clientCity!=''">
                                                <xsl:value-of select="clientCity" />
                                            </xsl:if>
                                            &#160;&#160;
                                            <xsl:if test="clientState!=''">
                                                <xsl:value-of select="clientState" />
                                            </xsl:if>&#160;&#160;
                                            <xsl:if test="clientZip!=''">
                                                <xsl:value-of select="clientZip" />
                                            </xsl:if>
                                        </fo:block>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell >
                                    <fo:block>
                                        <fo:table>
                                            <fo:table-column column-width="35%"/>
                                            <fo:table-column column-width="65%"/>
                                            <fo:table-body>
                                                <fo:table-row>
                                                    <fo:table-cell >
                                                        <fo:block font-size="9">Invoice Number</fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell >
                                                        <fo:block font-size="9" text-align="right"><xsl:value-of select="invoiceNumber" /> </fo:block>
                                                    </fo:table-cell>
                                                </fo:table-row>

                                                <xsl:if test="poNumber!=''">
                                                    <fo:table-row>
                                                        <fo:table-cell >
                                                            <fo:block font-size="9">Contract/PO Number</fo:block>
                                                        </fo:table-cell>
                                                        <fo:table-cell >
                                                            <fo:block font-size="9" text-align="right"><xsl:value-of select="poNumber" /> </fo:block>
                                                        </fo:table-cell>
                                                    </fo:table-row>
                                                </xsl:if>

                                                <xsl:if test="invoiceDate!=''">
                                                    <fo:table-row>
                                                        <fo:table-cell >
                                                            <fo:block font-size="9">Invoice Date</fo:block>
                                                        </fo:table-cell>
                                                        <fo:table-cell >
                                                            <fo:block font-size="9" text-align="right"><xsl:value-of select="invoiceDate" /> </fo:block>
                                                        </fo:table-cell>
                                                    </fo:table-row>
                                                </xsl:if>

                                                <xsl:if test="dueDate!=''">
                                                    <fo:table-row>
                                                        <fo:table-cell >
                                                            <fo:block font-size="9">Due Date</fo:block>
                                                        </fo:table-cell>
                                                        <fo:table-cell >
                                                            <fo:block font-size="9" text-align="right"><xsl:value-of select="dueDate" /> </fo:block>
                                                        </fo:table-cell>
                                                    </fo:table-row>
                                                </xsl:if>

                                                <xsl:if test="balanceDue!=''">
                                                    <fo:table-row>
                                                        <fo:table-cell >
                                                            <fo:block font-size="9">Balance Due</fo:block>
                                                        </fo:table-cell>
                                                        <fo:table-cell >
                                                            <fo:block font-size="9" text-align="right"><xsl:value-of select="balanceDue" /> </fo:block>
                                                        </fo:table-cell>
                                                    </fo:table-row>
                                                </xsl:if>

                                            </fo:table-body>
                                        </fo:table>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>

                    </fo:table>
                </fo:block>

                <fo:block space-before="5mm"  >
                    <fo:table>
                        <fo:table-column column-width="20%"/>
                        <fo:table-column column-width="50%"/>
                        <fo:table-column column-width="10%"/>
                        <fo:table-column column-width="10%"/>
                        <fo:table-column column-width="10%"/>

                        <fo:table-header  border-bottom-style="solid"  border-bottom-color="#D7D7D7">
                            <fo:table-row>
                                <fo:table-cell   padding-after="2mm" >
                                    <fo:block font-size="8"  font-weight="bold" text-align="left">Item</fo:block>
                                </fo:table-cell>
                                <fo:table-cell   padding-after="2mm">
                                    <fo:block font-size="8"  font-weight="bold" text-align="left">Description</fo:block>
                                </fo:table-cell>
                                <fo:table-cell   padding-after="2mm">
                                    <fo:block font-size="8"  font-weight="bold" text-align="right">Unit Cost</fo:block>
                                </fo:table-cell>
                                <fo:table-cell   padding-after="2mm">
                                    <fo:block font-size="8"  font-weight="bold" text-align="right">Quantity</fo:block>
                                </fo:table-cell>
                                <fo:table-cell   padding-after="2mm">
                                    <fo:block font-size="8"  font-weight="bold" text-align="right">Total</fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-header>

                        <fo:table-body>
                            <xsl:for-each select="items">
                                <fo:table-row>
                                    <fo:table-cell padding-before="2mm" >
                                        <fo:block font-size="9" text-align="left"><xsl:value-of select="itemName" /> </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell padding-before="2mm" >
                                        <fo:block font-size="9" text-align="left"><xsl:value-of select="itemDescription" /> </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell padding-before="2mm" >
                                        <fo:block font-size="9" text-align="right"><xsl:value-of select="unitCostDisplay" /> </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell padding-before="2mm" >
                                        <fo:block font-size="9" text-align="right"><xsl:value-of select="quantityDisplay" /> </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell padding-before="2mm" >
                                        <fo:block font-size="9" text-align="right"><xsl:value-of select="totalDisplay" /> </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </xsl:for-each>
                        </fo:table-body>
                    </fo:table>
                </fo:block>
                <fo:block space-before="5mm"  >
                    <fo:table>
                        <fo:table-column column-width="60%"/>
                        <fo:table-column column-width="40%"/>

                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block space-before="5mm"> &#160;&#160; </fo:block>
                                    <xsl:if test="terms!=''">
                                        <fo:block space-before="2mm" font-size="10" font-weight="bold"> Terms And Instructions </fo:block>
                                        <fo:block space-before="2mm" font-size="9"  text-indent="5mm">
                                            <xsl:value-of select="terms" />
                                        </fo:block>
                                    </xsl:if>
                                    <xsl:if test="note!=''">
                                        <fo:block space-before="2mm" font-size="10" font-weight="bold"> Note </fo:block>
                                        <fo:block space-before="2mm" font-size="9" text-indent="5mm">
                                            <xsl:value-of select="note" />
                                        </fo:block>
                                    </xsl:if>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block>

                                        <fo:table>
                                            <fo:table-column column-width="50%"/>
                                            <fo:table-column column-width="50%"/>

                                            <fo:table-body>
                                                <fo:table-row>
                                                    <fo:table-cell>
                                                        <fo:block>
                                                            <fo:block font-size="9" text-align="left">Subtotal</fo:block>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell>
                                                        <fo:block   font-size="9"  text-align="right">
                                                            <xsl:value-of select="subTotal" />
                                                        </fo:block>
                                                    </fo:table-cell>
                                                </fo:table-row>

                                                <xsl:if test="discount!=''">
                                                    <fo:table-row>
                                                        <fo:table-cell>
                                                            <fo:block>
                                                                <fo:block font-size="9" text-align="left">Discount</fo:block>
                                                            </fo:block>
                                                        </fo:table-cell>
                                                        <fo:table-cell>
                                                            <fo:block   font-size="9" text-align="right">
                                                                - <xsl:value-of select="discount" />
                                                            </fo:block>
                                                        </fo:table-cell>
                                                    </fo:table-row>
                                                </xsl:if>

                                                <xsl:if test="tax!=''">
                                                    <fo:table-row>
                                                        <fo:table-cell>
                                                            <fo:block>
                                                                <fo:block font-size="9"  text-align="left">Tax</fo:block>
                                                            </fo:block>
                                                        </fo:table-cell>
                                                        <fo:table-cell>
                                                            <fo:block  font-size="9"  text-align="right">
                                                                 <xsl:value-of select="tax" />
                                                            </fo:block>
                                                        </fo:table-cell>
                                                    </fo:table-row>
                                                </xsl:if>

                                                <fo:table-row>
                                                    <fo:table-cell padding-before="5mm" >
                                                        <fo:block>
                                                            <fo:block font-size="11" font-weight="bold"  text-align="left">Balance Due</fo:block>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell padding-before="5mm" >
                                                        <fo:block font-size="11" font-weight="bold"   text-align="right">
                                                             <xsl:value-of select="balanceDue" />
                                                        </fo:block>
                                                    </fo:table-cell>
                                                </fo:table-row>

                                            </fo:table-body>
                                        </fo:table>

                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>
                </fo:block>


            </fo:flow>
        </fo:page-sequence>
    </fo:root>
    </xsl:template>
    <xsl:attribute-set name="myBorder">
        <xsl:attribute name="border">solid 0.1mm black</xsl:attribute>
    </xsl:attribute-set>
    <xsl:template match="new_line">
        <br></br>
    </xsl:template>
</xsl:stylesheet>