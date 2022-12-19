package ink.markidea.note.util;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.KeyPair;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;

@Slf4j
public class SshUtil {


    public static String genAndStoreKeyPair(String prvKeyFileName, String pubKeyFileName, File sshKeyDir){
        JSch jSch = new JSch();
        try {
            //生成密钥
            KeyPair keyPair = KeyPair.genKeyPair(jSch, KeyPair.ECDSA,521);
            File prvFile = new File(sshKeyDir, prvKeyFileName);
            File pubFile = new File(sshKeyDir, pubKeyFileName);

            //将密钥写入用户目录
            keyPair.writePrivateKey(new FileOutputStream(prvFile));
            keyPair.writePublicKey(new FileOutputStream(pubFile), null);
            keyPair.dispose();

            return FileUtil.readFileAsString(pubFile);
        } catch (Exception e) {
            log.error("generate ssh key failed ", e);
            return null;
        }

    }

    public static String genAndStoreKeyPair(String prvKeyFileName, String pubKeyFileName, String sshKeyDir){
        return genAndStoreKeyPair(prvKeyFileName, pubKeyFileName, new File(sshKeyDir));

    }

}
