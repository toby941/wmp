package de.brendamour.jpasskit.create;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import de.brendamour.jpasskit.PKBarcode;
import de.brendamour.jpasskit.PKField;
import de.brendamour.jpasskit.PKLocation;
import de.brendamour.jpasskit.PKPass;
import de.brendamour.jpasskit.enums.PKBarcodeFormat;
import de.brendamour.jpasskit.passes.PKEventTicket;
import de.brendamour.jpasskit.signing.PKSigningInformation;
import de.brendamour.jpasskit.signing.PKSigningUtil;

public class XiduoWu {
    public static void zipPass(PKPass pass) throws Exception {
        String passKey = "D:\\workspace\\emms_maven\\src\\main\\webapp\\WEB-INF\\config\\passbookkey\\pass.p12";
        String password = "sonic333";
        String appleFile =
                "D:\\workspace\\emms_maven\\src\\main\\webapp\\WEB-INF\\config\\passbookkey\\AppleWWDRCA.pem";
        String pathToTemplateDirectory = "D:/tmp/passbook/t";
        PKSigningInformation pkSigningInformation =
                PKSigningUtil.loadSigningInformationFromPKCS12FileAndIntermediateCertificateFile(passKey, password,
                        appleFile);
        byte[] passZipAsByteArray =
                PKSigningUtil.createSignedAndZippedPkPassArchive(pass, pathToTemplateDirectory, pkSigningInformation);
        // FileUtils
        // .forceDelete(new File("D:\\workspace\\emms_maven\\src\\main\\webapp\\WEB-INF\\passbook\\kairun.pkpass"));
        FileUtils.writeByteArrayToFile(new File(
                "D:\\workspace\\emms_maven\\src\\main\\webapp\\WEB-INF\\passbook\\xiduowo.pkpass"), passZipAsByteArray);

    }

    @Test
    public void createNormal() throws Exception {
        PKPass pass = new PKPass();

        PKEventTicket genericCard = new PKEventTicket();

        List<PKField> primaryFields = new ArrayList<PKField>();

        PKField balanceField = new PKField();
        balanceField.setKey("Test Date");
        balanceField.setLabel("有效期");
        balanceField.setValue("2012-11-23");

        List<PKField> seondFields = new ArrayList<PKField>();
        PKField seondField = new PKField();
        seondField.setKey("Test Dates");
        seondField.setLabel("奖品");
        seondField.setValue("喜多屋畅吃券");
        seondFields.add(seondField);

        primaryFields.add(balanceField);
        primaryFields.add(seondField);
        // genericCard.setPrimaryFields(primaryFields);
        genericCard.setSecondaryFields(primaryFields);
        // genericCard.setAuxiliaryFields(seondFields);

        List<PKField> backFields = new ArrayList<PKField>();
        PKField backField = new PKField();
        backField.setKey("Test Date");
        backField.setLabel("活动规则");
        backField
                .setValue("活动时间：2012年11月20日——11月23日\r\n活动内容：下载活动奖券即可参与抽奖，有机会获得价值158元的喜多屋畅吃美食劵一张，开奖时间：12月23日中午12:00\r\n获奖人数：5名\r\n本次活动仅限南京纬思武德投资实业有限公司员工参加。");
        PKField airField = new PKField();
        airField.setKey("airad");
        airField.setLabel("制作");
        airField.setValue("大气创媒 http://www.airad.com");
        backFields.add(backField);
        backFields.add(airField);
        genericCard.setBackFields(backFields);

        PKBarcode barcode = new PKBarcode();
        barcode.setFormat(PKBarcodeFormat.PKBarcodeFormatQR);
        barcode.setMessage("http://emms.airad.com/passbook/down/t/xiduowu.pkpass");
        barcode.setMessageEncoding(Charset.forName("utf-8"));
        // pass.setBarcode(barcode);

        Date d = Calendar.getInstance().getTime();
        Date d2 = DateUtils.addHours(d, 4);
        pass.setRelevantDate(d2);

        pass.setLocations(getLocations());

        pass.setFormatVersion(1);
        pass.setDescription("*Event");
        pass.setOrganizationName("喜多屋畅吃券");
        pass.setPassTypeIdentifier("pass.com.airad.toby");
        pass.setTeamIdentifier("QBHE7996Q5");
        pass.setSerialNumber("kairuntest");

        pass.setLogoText("艾尚天地抽奖券");
        pass.setEventTicket(genericCard);
        pass.setBackgroundColor("rgb(255,0,125)");
        pass.setForegroundColor("rgb(200,200,200)");
        pass.setLabelColor("rgb(35,25,20)");

        // pass.setWebServiceURL(new URL("https://api.passtools.com/apple/"));
        pass.setWebServiceURL(new URL("http://192.168.1.13:9091/passapi/"));
        // pass.setWebServiceURL(new URL("https://emms.airad.com/passapi/"));
        // pass.setWebServiceURL(new URL("http://192.168.1.247:8860/passapi/"));
        pass.setAuthenticationToken("s1jpIl6nsdqDI2LAskBz5w==");
        zipPass(pass);
    }

    private List<PKLocation> getLocations() {
        List<PKLocation> locationList = new ArrayList<PKLocation>();
        Double[] ds = new Double[]{};
        PKLocation l1 = new PKLocation();
        // l1.setLatitude(32.078606); // 维度
        // l1.setLongitude(118.742677);// 经度
        // l1.setRelevantText("智慧谷");
        l1.setLatitude(32.071583); // 维度
        l1.setLongitude(118.761735);// 经度
        l1.setRelevantText("竹林新村 ");

        locationList.add(l1);
        return locationList;
    }
}
