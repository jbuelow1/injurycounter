package com.jbuelow.injurycounter.ui.component.live;

import java.awt.Font;
import javax.annotation.PostConstruct;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import org.springframework.stereotype.Component;

@Component
public class LiveTitle extends JLabel {

  public LiveTitle() {
    super("", SwingConstants.CENTER);
  }

  @PostConstruct
  public void init() {
    setText("Time Since Injury");
    setFont(new Font(getFont().getName(), getFont().getStyle(), 85));
  }

}
