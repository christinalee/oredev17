package support;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlatformTypes {

    public static List<String> getAList() {
        return new ArrayList<>();
    }

    public static List<String> returnsNullList() {
        return null;
    }
}
