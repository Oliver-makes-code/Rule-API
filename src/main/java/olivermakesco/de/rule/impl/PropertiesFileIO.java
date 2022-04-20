package olivermakesco.de.rule.impl;

import net.minecraft.util.Identifier;
import olivermakesco.de.rule.api.Rules;
import org.quiltmc.loader.api.QuiltLoader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;
import java.util.Set;

public class PropertiesFileIO {
    public static void read() throws IOException {
        var path = Path.of(QuiltLoader.getConfigDir().toString(), "rule_api.properties");
        if (!path.toFile().exists()) {
            path.toFile().createNewFile();
            return;
        }
        Properties properties = new Properties();
        properties.load(path.toFile().toURI().toURL().openStream());
        Set<String> keys = properties.stringPropertyNames();
        for (var key : keys) {
            var value = properties.getProperty(key);
            var id = Identifier.splitOn(key, ';');
            Rules.get(id).setValue(value);
        }
    }
    public static void write() throws IOException {
        var path = Path.of(QuiltLoader.getConfigDir().toString(), "rule_api.properties");
        if (!path.toFile().exists()) {
            path.toFile().createNewFile();
        }
        Properties properties = new Properties();
        for (var key : Rules.getIds())
            properties.setProperty(key.toString().replace(':',';'), Rules.get(key).getValue().toString());
        properties.store(new FileOutputStream(path.toFile()), null);
    }
}
